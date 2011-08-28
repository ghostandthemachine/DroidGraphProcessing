package com.droidgraph.transformation;

public class Vec3f {

	public float x;
	public float y;
	public float z;

	/**
	 * The values of transformation matrix
	 */
	double m00;
	double m10;
	double m01;
	double m11;
	double m02;
	double m12;
	double m03;
	double m13;

	public Vec3f(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vec3f(float x, float y) {
		this.x = x;
		this.y = y;
		this.z = 0;
	}

	public Vec3f() {
		this(0, 0, 0);
		m00 = m11 = 1.0;
		m10 = m01 = m02 = m12 = m03 = m13 = 0.0;
	}

	public Vec3f(Vec3f v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
		this.m00 = v.m00;
		this.m01 = v.m01;
		this.m02 = v.m02;
		this.m03 = v.m03;
		this.m13 = v.m10;
		this.m11 = v.m11;
		this.m12 = v.m12;
		this.m13 = v.m13;
	}

	/**
	 * @return the x
	 */
	public float getX() {
		return x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public float getY() {
		return y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * @return the z
	 */
	public float getZ() {
		return z;
	}

	/**
	 * @param z
	 *            the z to set
	 */
	public void setZ(float z) {
		this.z = z;
	}

	/**
	 * @param add
	 *            one Vec3f to another
	 */
	public void add(Vec3f vec3f) {
		x += vec3f.x;
		y += vec3f.y;
		z += vec3f.z;
	}

	public Vec3f inverseTransform(Vec3f src, Vec3f dst) {
		double det = getDeterminant();

		if (dst == null) {
			if (src instanceof Vec3f) {
				dst = new Vec3f();
			} else {
				dst = new Vec3f();
			}
		}

		double x = src.getX() - m02;
		double y = src.getY() - m12;

		dst.set((x * m11 - y * m01) / det, (y * m00 - x * m10) / det, 0);
		return dst;
	}

	public void set(double x, double y, double z) {
		this.x = (float) x;
		this.y = (float) y;
		this.z = (float) z;

	}

	public double getDeterminant() {
		return m00 * m11 - m01 * m10 - m02 * m12;
	}

	public void set(Vec3f v) {
		this.x = v.x;
		this.y = v.y;
		this.z = v.z;
	}
	
    public Vec3f transform(Vec3f src, Vec3f dst) {
        if (dst == null) {
            if (src instanceof  Vec3f) {
                dst = new Vec3f();
            } else {
                dst = new Vec3f();
            }
        }

        double x = src.getX();
        double y = src.getY();

        dst.set(x * m00 + y * m01 + m02, x * m10 + y * m11
                + m12, 0);
        return dst;
    }

	public void translate(float translateX, float translateY, float translateZ) {
		this.x += translateX;
		this.y += translateY;
		this.z += translateZ;
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append("x:" + x);
		s.append(", ");
		s.append("y:" + y);
		s.append(", ");
		s.append("z:" + z);
		s.append(", ");
		return s.toString();
	}

	public Bounds createTransformedShape(Bounds subregionBounds) {
		Bounds b = subregionBounds;
		b.transform(this);
		return b;
	}


}
