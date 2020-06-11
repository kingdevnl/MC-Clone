layout (location=0) in vec3 pos;

uniform mat4 projectionMatrix;
uniform mat4 worldViewMatrix;
layout (location=1) in vec2 texCoord;

out vec4 pass_poss;
out vec2 outTexCoord;

void main() {
    pass_poss = vec4(pos, 1.0);
    gl_Position = projectionMatrix * worldViewMatrix * pass_poss;
    outTexCoord = texCoord;
}