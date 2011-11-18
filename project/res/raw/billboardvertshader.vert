uniform mat4 projMat;
uniform mat4 viewMat;
uniform vec4 objectPos;

attribute vec4 vPosition;
attribute vec4 vColor;

varying vec4 fragColor;

void main()
{
    vec4 cameraSpaceObjectPos = viewMat * objectPos, cameraSpacePos = viewMat * vPosition;
    
    //float angle = atan(cameraSpaceObjectPos.z,cameraSpaceObjectPos.x);
    
    //vColor[0] = angle;
    
    fragColor = vColor;
    
    gl_Position = projMat * cameraSpacePos;
}