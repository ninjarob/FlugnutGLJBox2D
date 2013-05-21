package com.pkp.gameengine.io.impl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import com.pkp.gameengine.IScreen;

public class Vertices {
	final boolean hasColor;
	final boolean hasTexCoords;
	final boolean background;
	final int vertexSize;
	final FloatBuffer vertices;
	final ShortBuffer indices;

	public Vertices(int maxVertices, int maxIndices,
			boolean hasColor, boolean hasTexCoords, boolean background) {
		this.hasColor = hasColor;
		this.hasTexCoords = hasTexCoords;
		this.background = background;
		this.vertexSize = (2 + (hasColor ? 4 : 0) + (hasTexCoords ? 2 : 0) + (background ? 2 : 0)) * 4;
		ByteBuffer buffer = ByteBuffer.allocateDirect(maxVertices * vertexSize);
		buffer.order(ByteOrder.nativeOrder());
		vertices = buffer.asFloatBuffer();

		if (maxIndices > 0) {
			buffer = ByteBuffer.allocateDirect(maxIndices * Short.SIZE / 8);
			buffer.order(ByteOrder.nativeOrder());
			indices = buffer.asShortBuffer();
		} else {
			indices = null;
		}
	}

	public void setVertices(float[] vertices, int offset, int length) {
		this.vertices.clear();
		this.vertices.put(vertices, offset, length);
		this.vertices.flip();
	}

	public void setIndices(short[] indices, int offset, int length) {
		this.indices.clear();
		this.indices.put(indices, offset, length);
		this.indices.flip();
	}
	
	public ShortBuffer getIndices()
	{
		return this.indices;
	}

	public void draw(int primitiveType, int offset, int numVertices) {
		GL10 gl = IScreen.glGraphics.getGL();
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		vertices.position(0);
		if (!background) {
			gl.glVertexPointer(2, GL10.GL_FLOAT, vertexSize, vertices);
		}
		else {
			gl.glVertexPointer(3, GL10.GL_FLOAT, vertexSize, vertices);
		}
		if (hasColor) {
			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
			if (!background) {
				vertices.position(2);
			}
			else {
				vertices.position(3);
			}
			gl.glColorPointer(4, GL10.GL_FLOAT, vertexSize, vertices);
		}
		if (hasTexCoords) {
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			if (!background) {
				vertices.position(hasColor ? 6 : 2);
			}
			else {
				vertices.position(hasColor ? 7 : 3);
			}
			gl.glTexCoordPointer(3, GL10.GL_FLOAT, vertexSize, vertices);
		}
		if (indices != null) {
			indices.position(offset);
			gl.glDrawElements(primitiveType, numVertices,
					GL10.GL_UNSIGNED_SHORT, indices);
		} else {
			gl.glDrawArrays(primitiveType, offset, numVertices);
		}
		if (hasTexCoords)
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		if (hasColor)
			gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	}
}
