uniform mat4 uMVPMatrix;

attribute vec4 aPosition;

varying vec2 vTextureCoord;

void main() {
    gl_Position = vec4(aPosition.xy, 0, 1);
    vTextureCoord = aPosition.xy * 0.5 + 0.5;
}