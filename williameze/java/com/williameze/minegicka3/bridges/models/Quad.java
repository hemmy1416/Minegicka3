package com.williameze.minegicka3.bridges.models;

import org.lwjgl.opengl.GL11;

import com.williameze.minegicka3.bridges.math.Line;
import com.williameze.minegicka3.bridges.math.Plane;
import com.williameze.minegicka3.bridges.math.Vector;

import net.minecraft.client.renderer.Tessellator;

public class Quad extends ModelObject
{
    public Vector v1, v2, v3, v4;
    public Vector normal;
    public double currentScale = 1;

    public Quad(Vector vec1, Vector vec2, Vector vec3, Vector vec4)
    {
	this(vec1, vec2, vec3, vec4,
		new Vector((vec1.x + vec2.x + vec3.x + vec4.x) / 4, (vec1.y + vec2.y + vec3.y + vec4.y) / 4, (vec1.z + vec2.z + vec3.z + vec4.z) / 4), false);
    }

    public Quad(Vector vec1, Vector vec2, Vector vec3, Vector vec4, Vector nor, boolean setNormalDirectly)
    {
	v1 = vec1;
	v2 = vec2;
	v3 = vec3;
	v4 = vec4;
	if (setNormalDirectly)
	{
	    normal = nor.normalize();
	}
	else
	{
	    Vector n1 = v2.subtract(v1).crossProduct(v3.subtract(v1));
	    Vector n2 = v2.subtract(v4).crossProduct(v3.subtract(v4));
	    Vector n = n1.add(n2).normalize();
	    if (n.dotProduct(nor) >= 0) normal = n;
	    else normal = n.multiply(-1);
	}
	orderVertexesCounterClockwise();
    }
    
    public Quad orderVertexesCounterClockwise()
    {
	Vector mid = getMidVec();
	Line normalLine = new Line(mid, normal);
	
	Vector pointV1 = v1.subtract(normalLine.intersectWith(new Plane(normal, v1)));
	Vector pointV2 = v2.subtract(normalLine.intersectWith(new Plane(normal, v2)));
	Vector pointV3 = v3.subtract(normalLine.intersectWith(new Plane(normal, v3)));
	Vector pointV4 = v4.subtract(normalLine.intersectWith(new Plane(normal, v4)));
	double angleV2 = pointV2.getAlgebraicAngle(pointV1);
	double angleV3 = pointV3.getAlgebraicAngle(pointV1);
	double angleV4 = pointV4.getAlgebraicAngle(pointV1);
	if(normal.dotProduct(pointV1.crossProduct(pointV2))<0) angleV2+=Math.PI;
	if(normal.dotProduct(pointV1.crossProduct(pointV3))<0) angleV3+=Math.PI;
	if(normal.dotProduct(pointV1.crossProduct(pointV4))<0) angleV4+=Math.PI;
	
	Vector dummy2, dummy3, dummy4;
	if(angleV2>=angleV3 && angleV2>=angleV4)
	{
	    dummy4 = v2;
	    if(angleV4>angleV3)
	    {
		dummy3 = v4;
		dummy2 = v3;
	    }
	    else
	    {
		dummy3 = v3;
		dummy2 = v4;
	    }
	}
	else if(angleV3>=angleV2 && angleV3>=angleV4)
	{
	    dummy4 = v3;
	    if(angleV4>angleV2)
	    {
		dummy3 = v4;
		dummy2 = v2;
	    }
	    else
	    {
		dummy3 = v2;
		dummy2 = v4;
	    }
	}
	else
	{
	    dummy4 = v4;
	    if(angleV3>angleV2)
	    {
		dummy3 = v3;
		dummy2 = v2;
	    }
	    else
	    {
		dummy3 = v2;
		dummy2 = v3;
	    }
	}
	v2 = dummy2;
	v3 = dummy3;
	v4 = dummy4;
	return this;
    }

    public Vector getMidVec()
    {
	return new Vector((v1.x + v2.x + v3.x + v4.x) / 4, (v1.y + v2.y + v3.y + v4.y) / 4, (v1.z + v2.z + v3.z + v4.z) / 4);
    }
    
    public Quad scaleRelative(double d)
    {
	return scaleDefinite(d*currentScale);
    }

    public Quad scaleDefinite(double d)
    {
	Vector mid = getMidVec();
	v1 = mid.add(v1.subtract(mid).multiply(d / currentScale));
	v2 = mid.add(v2.subtract(mid).multiply(d / currentScale));
	v3 = mid.add(v3.subtract(mid).multiply(d / currentScale));
	v4 = mid.add(v4.subtract(mid).multiply(d / currentScale));
	currentScale = d;
	return this;
    }
    
    public Quad translate(Vector v)
    {
	v1 = v1.add(v);
	v2 = v2.add(v);
	v3 = v3.add(v);
	v4 = v4.add(v);
	return this;
    }
    
    public Quad rotateAroundNormal(double rad)
    {
	Vector mid = getMidVec();
	return rotate(mid, normal, rad);
    }
    
    public Quad rotate(Vector pivot, Vector axis, double rad)
    {
	v1 = v1.subtract(pivot).rotateAround(axis, rad).add(pivot);
	v2 = v2.subtract(pivot).rotateAround(axis, rad).add(pivot);
	v3 = v3.subtract(pivot).rotateAround(axis, rad).add(pivot);
	v4 = v4.subtract(pivot).rotateAround(axis, rad).add(pivot);
	return this;
    }

    public void addQuadToTess(Tessellator tess)
    {
	GL11.glNormal3d(normal.x, normal.y, normal.z);
	tess.addVertex(v1.x, v1.y, v1.z);
	tess.addVertex(v2.x, v2.y, v2.z);
	tess.addVertex(v3.x, v3.y, v3.z);
	tess.addVertex(v4.x, v4.y, v4.z);
    }

    @Override
    public void render()
    {
	GL11.glPushMatrix();
	tess.startDrawingQuads();
	tess.setColorRGBA_I(color, opacity);
	addQuadToTess(tess);
	tess.draw();
	GL11.glPopMatrix();
    }
}
