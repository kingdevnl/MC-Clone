layout (location=0) in vec3 pos;

uniform mat4 projectionMatrix;
uniform mat4 worldViewMatrix;


out vec4 pass_poss;
out vec3 test;

void main() {
    pass_poss = vec4(pos, 1.0);
    gl_Position = projectionMatrix * worldViewMatrix * pass_poss;
}