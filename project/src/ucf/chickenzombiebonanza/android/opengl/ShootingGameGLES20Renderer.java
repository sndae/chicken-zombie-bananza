package ucf.chickenzombiebonanza.android.opengl;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import ucf.chickenzombiebonanza.common.LocalOrientation;
import ucf.chickenzombiebonanza.common.sensor.OrientationListener;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

public class ShootingGameGLES20Renderer implements GLSurfaceView.Renderer, OrientationListener {
	
    private final String vertexShaderCode = 
        "uniform mat4 uMVPMatrix;   \n" +            
        "attribute vec4 vPosition;  \n" +
        "void main(){               \n" +
        " gl_Position = uMVPMatrix * vPosition; \n" +
        "}  \n";

    private final String fragmentShaderCode = 
        "precision mediump float;  \n" + 
        "uniform vec4 vColor;  \n" + 
        "void main(){              \n" + 
        " gl_FragColor = vColor; \n" + 
        "}                         \n";

    private int mProgram;
    private int maPositionHandle;
    private int colorHandle;
    
    private int muMVPMatrixHandle;
    private float[] mMVPMatrix = new float[16];
    //private float[] translateViewMatrix = new float[16];
    private float[] rotationViewMatrix = new float[16];
    //private float[] mVMatrix = new float[16];
    private float[] mProjMatrix = new float[16];

    private FloatBuffer frontTriangleVB, rightTriangleVB, upTriangleVB;
    
    private FloatBuffer frontTriangleCB, rightTriangleCB, upTriangleCB;

    @Override
    public void onSurfaceCreated(GL10 unused, EGLConfig config) {
        GLES20.glClearColor(0.5f, 0.5f, 0.5f, 1.0f);

        int vertexShader = loadShader(GLES20.GL_VERTEX_SHADER, vertexShaderCode);
        int fragmentShader = loadShader(GLES20.GL_FRAGMENT_SHADER, fragmentShaderCode);

        mProgram = GLES20.glCreateProgram(); // create empty OpenGL Program
        GLES20.glAttachShader(mProgram, vertexShader); // add the vertex shader to program
        GLES20.glAttachShader(mProgram, fragmentShader); // add the fragment shader to program
        GLES20.glLinkProgram(mProgram); // creates OpenGL program executables

        // get handle to the vertex shader's vPosition member
        maPositionHandle = GLES20.glGetAttribLocation(mProgram, "vPosition");
        colorHandle = GLES20.glGetUniformLocation(mProgram, "vColor");
        
        muMVPMatrixHandle = GLES20.glGetUniformLocation(mProgram, "uMVPMatrix");
        
        Matrix.setIdentityM(rotationViewMatrix, 0);

        initShapes();
    }
	
    @Override
    public void onDrawFrame(GL10 unused) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

        // Add program to OpenGL environment
        GLES20.glUseProgram(mProgram);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjMatrix, 0, rotationViewMatrix, 0);
        GLES20.glUniformMatrix4fv(muMVPMatrixHandle, 1, false, mMVPMatrix, 0);

        //Draw Front Triangle
        
        // Prepare the triangle data
        GLES20.glVertexAttribPointer(maPositionHandle, 3, GLES20.GL_FLOAT, false, 12, frontTriangleVB);
        GLES20.glEnableVertexAttribArray(maPositionHandle);
        GLES20.glUniform4fv(colorHandle, 1, frontTriangleCB);

        // Draw the triangle
        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
        
        //Draw Up Triangle        
        GLES20.glVertexAttribPointer(maPositionHandle, 3, GLES20.GL_FLOAT, false, 12, upTriangleVB);
        GLES20.glEnableVertexAttribArray(maPositionHandle);
        GLES20.glUniform4fv(colorHandle, 1, upTriangleCB);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);
        
        //Draw Right Triangle        
        GLES20.glVertexAttribPointer(maPositionHandle, 3, GLES20.GL_FLOAT, false, 12, rightTriangleVB);
        GLES20.glEnableVertexAttribArray(maPositionHandle);
        GLES20.glUniform4fv(colorHandle, 1, rightTriangleCB);

        GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 3);

    }

	@Override
	public void onSurfaceChanged(GL10 unused, int width, int height) {
		GLES20.glViewport(0, 0, width, height);
		
		float ratio = (float) width / height;
        
        // this projection matrix is applied to object coodinates
        // in the onDrawFrame() method
        Matrix.frustumM(mProjMatrix, 0, -ratio, ratio, -1f, 1f, 0.1f, 10f);
	}
	
    private int loadShader(int type, String shaderCode) {

        // create a vertex shader type (GLES20.GL_VERTEX_SHADER)
        // or a fragment shader type (GLES20.GL_FRAGMENT_SHADER)
        int shader = GLES20.glCreateShader(type);

        // add the source code to the shader and compile it
        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }

    private void initShapes() {

        float frontTriangleCoords[] = {
            -0.5f, -0.25f,        3.0f, 
             0.5f, -0.25f,        3.0f, 
             0.0f,  0.559016994f, 3.0f };

        // initialize vertex Buffer for triangle
        ByteBuffer vbb = ByteBuffer.allocateDirect(frontTriangleCoords.length * 4);
        vbb.order(ByteOrder.nativeOrder());// use the device hardware's native byte order
        frontTriangleVB = vbb.asFloatBuffer(); // create a floating point buffer from the ByteBuffer
        frontTriangleVB.put(frontTriangleCoords); // add the coordinates to the FloatBuffer
        frontTriangleVB.position(0); // set the buffer to read the first coordinate

        float frontColor[] = {1.0f, 0.0f, 0.0f, 1.0f};
        vbb = ByteBuffer.allocateDirect(frontColor.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        frontTriangleCB = vbb.asFloatBuffer();
        frontTriangleCB.put(frontColor);
        frontTriangleCB.position(0);

        float upTriangleCoords[] = { 
            -0.5f, 3.0f, -0.25f, 
             0.5f, 3.0f, -0.25f, 
             0.0f, 3.0f,  0.559016994f};

        vbb = ByteBuffer.allocateDirect(upTriangleCoords.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        upTriangleVB = vbb.asFloatBuffer();
        upTriangleVB.put(upTriangleCoords);
        upTriangleVB.position(0);
        
        float upColor[] = {0.0f, 1.0f, 0.0f, 1.0f};
        vbb = ByteBuffer.allocateDirect(upColor.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        upTriangleCB = vbb.asFloatBuffer();
        upTriangleCB.put(upColor);
        upTriangleCB.position(0);
        
        float rightTriangleCoords[] = { 
            3.0f, -0.25f,        -0.5f, 
            3.0f, -0.25f,         0.5f,
            3.0f,  0.559016994f,  0.0f};

        vbb = ByteBuffer.allocateDirect(rightTriangleCoords.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        rightTriangleVB = vbb.asFloatBuffer();
        rightTriangleVB.put(rightTriangleCoords);
        rightTriangleVB.position(0);
        
        float rightColor[] = {0.0f, 0.0f, 1.0f, 1.0f};
        vbb = ByteBuffer.allocateDirect(rightColor.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        rightTriangleCB = vbb.asFloatBuffer();
        rightTriangleCB.put(rightColor);
        rightTriangleCB.position(0);

    }

    @Override
    public void receiveOrientationUpdate(LocalOrientation orientation) {
        Log.d("Renderer",orientation.toString());
        Matrix.setIdentityM(rotationViewMatrix, 0);
        rotationViewMatrix[0] = (float) orientation.getRight().u();
        rotationViewMatrix[4] = (float) orientation.getRight().v();
        rotationViewMatrix[8] = (float) orientation.getRight().w();
        
        rotationViewMatrix[1] = (float) orientation.getUp().u();
        rotationViewMatrix[5] = (float) orientation.getUp().v();
        rotationViewMatrix[9] = (float) orientation.getUp().w();
        
        rotationViewMatrix[2] = (float) orientation.getLookAt().u();
        rotationViewMatrix[6] = (float) orientation.getLookAt().v();
        rotationViewMatrix[10] = (float) orientation.getLookAt().w();
        
        Matrix.invertM(rotationViewMatrix, 0, rotationViewMatrix, 0);
    }

}
