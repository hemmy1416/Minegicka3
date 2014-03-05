package com.williameze.minegicka3.bridges.math;

public class Vector
{
    /**
     * X coordinate of VectorD
     */
    public double x;
    /**
     * Y coordinate of VectorD
     */
    public double y;
    /**
     * Z coordinate of VectorD
     */
    public double z;

    public Vector(double i, double j, double k)
    {
	x = i;
	y = j;
	z = k;
    }

    /**
     * Sets the x,y,z components of the vector as specified.
     */
    protected Vector setComponents(double par1, double par3, double par5)
    {
	x = par1;
	y = par3;
	z = par5;
	return this;
    }

    public Vector multiply(double d)
    {
	x *= d;
	y *= d;
	z *= d;
	return this;
    }

    /**
     * Normalizes the vector to a length of 1 (except if it is the zero vector)
     */
    public Vector normalize()
    {
	double d0 = Math.sqrt(x * x + y * y + z * z);
	return d0 < 1.0E-4D ? new Vector(0.0D, 0.0D, 0.0D) : new Vector(x / d0, y / d0, z / d0);
    }
    
    public double getAlgebraicAngle(Vector v)
    {
	return Math.atan2(crossProduct(v).lengthVector(), dotProduct(v));
    }

    public double dotProduct(Vector v)
    {
	return x * v.x + y * v.y + z * v.z;
    }

    /**
     * Returns a new vector with the result of this vector x the specified
     * vector.
     */
    public Vector crossProduct(Vector v)
    {
	return new Vector(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x);
    }

    public Vector add(double i, double j, double k)
    {
	return new Vector(x + i, y + j, z + k);
    }

    /**
     * Adds the specified x,y,z vector components to this vector and returns the
     * resulting vector. Does not change this vector.
     */
    public Vector add(Vector v)
    {
	return new Vector(x + v.x, y + v.y, z + v.z);
    }

    /**
     * Returns a new vector with the result of this vector minus the specified
     * vector
     */
    public Vector subtract(Vector v)
    {
	return new Vector(x - v.x, y - v.y, z - v.z);
    }

    /**
     * Returns the length of the vector.
     */
    public double lengthVector()
    {
	return Math.sqrt(x * x + y * y + z * z);
    }

    public String toString()
    {
	return "(" + x + ", " + y + ", " + z + ")";
    }

    public Vector rotateTowards(Vector dest)
    {
	Vector c = crossProduct(dest);
	double l = c.lengthVector();
	if (l > 0)
	{
	    Vector axis = c.normalize();
	    return rotateAround(axis, Math.atan2(l, dotProduct(dest)));
	}
	else if (dotProduct(dest) < 0)
	{
	    return new Vector(-c.x, -c.y, -c.z);
	}
	else
	{
	    return c;
	}
    }

    public Vector rotateAround(Vector axis, double radian)
    {
	Vector ax = axis.normalize();
	double cos = Math.cos(radian);
	double sin = Math.sin(radian);
	Vector seg1 = new Vector(x * cos, y * cos, z * cos);
	Vector seg2 = crossProduct(ax).multiply(sin);
	Vector seg3 = ax.multiply(dotProduct(ax) * (1 - cos));
	return new Vector(seg1.x + seg2.x + seg3.x, seg1.y + seg2.y + seg3.y, seg1.z + seg2.z + seg3.z);
    }

    /**
     * Rotates the vector around the x axis by the specified angle.
     */
    public void rotateAroundX(double par1)
    {
	double f1 = Math.cos(par1);
	double f2 = Math.sin(par1);
	double d0 = x;
	double d1 = y * f1 + z * f2;
	double d2 = z * f1 - y * f2;
	x = d0;
	y = d1;
	z = d2;
    }

    /**
     * Rotates the vector around the y axis by the specified angle.
     */
    public void rotateAroundY(double par1)
    {
	double f1 = Math.cos(par1);
	double f2 = Math.sin(par1);
	double d0 = x * f1 + z * f2;
	double d1 = y;
	double d2 = z * f1 - x * f2;
	x = d0;
	y = d1;
	z = d2;
    }

    /**
     * Rotates the vector around the z axis by the specified angle.
     */
    public void rotateAroundZ(double par1)
    {
	double f1 = Math.cos(par1);
	double f2 = Math.sin(par1);
	double d0 = x * f1 + y * f2;
	double d1 = y * f1 - x * f2;
	double d2 = z;
	x = d0;
	y = d1;
	z = d2;
    }

    public boolean isZeroVector()
    {
	return x == 0 && y == 0 && z == 0;
    }

    public boolean parallel(Vector v)
    {
	int clamp = 100000000;
	return Math.round(crossProduct(v).lengthVector() * clamp) == 0;
    }

    public Vector copy()
    {
	return new Vector(x, y, z);
    }
}