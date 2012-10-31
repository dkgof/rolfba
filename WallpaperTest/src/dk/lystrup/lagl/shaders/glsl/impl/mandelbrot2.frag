precision highp float;

uniform vec2 Pan;
uniform float Zoom;
uniform float Aspect;
uniform vec3 ColorScale;
uniform int Iterations;

varying vec2 vTextureCoord;

float computeValue(vec2 v, vec2 offset) {
    int iteration = 0;

    vec3 v2;

    for(iteration = 0; iteration < Iterations; iteration++) {
        v2 = v.xyy * v.xyx;

        v = vec2(v2.x - v2.y, 2.0 * v2.z) + offset;

        if ((v2.x + v2.y) > 2.0) {
            break;
        }
    }

    return (float(iteration) - (log(log(sqrt(v2.x + v2.y))) / log(2.0))) / float(Iterations);
}

void main() {
    vec2 v = (vTextureCoord - 0.5) * Zoom * vec2(1, Aspect) - Pan;

    float val = computeValue(v, v);

    gl_FragColor = vec4(sin(val * ColorScale.x), sin(val * ColorScale.y), sin(val * ColorScale.z), 1);
}