precision mediump float;

uniform sampler2D uTexture;
uniform vec2 center;
uniform float scale;
//uniform int iterations;

const int iterations = 8;

varying vec2 vTextureCoord;

void main() {
    vec2 z, c;

    c.x = 1.3333 * (vTextureCoord.x - 0.5) * scale - center.x;
    c.y = (vTextureCoord.y - 0.5) * scale - center.y;

    int i;
    z = c;
    for(i=0; i<iterations; i++) {
        float x = (z.x * z.x - z.y * z.y) + c.x;
        float y = (z.y * z.x + z.x * z.y) + c.y;

        if((x * x + y * y) > 4.0) break;
        z.x = x;
        z.y = y;
    }

    gl_FragColor = texture2D(uTexture, vec2((i == iterations ? 0.0 : float(i)) / 100.0,0));
}