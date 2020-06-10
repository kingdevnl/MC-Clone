layout (location=0) in vec3 pos;

uniform mat4 projectionMatrix;

out vec4 pass_poss;


void main() {
    pass_poss = vec4(pos, 1.0);
    gl_Position = projectionMatrix * pass_poss;
}