in vec4 pass_poss;
out vec3 color;


void main() {
    color = vec3(pass_poss.x+0.5, 0.5, pass_poss.y+0.5);
}