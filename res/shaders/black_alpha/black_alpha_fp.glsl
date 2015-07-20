#version 120

uniform sampler2D u_texture;
varying vec2 v_texcoords;

void main() {
	vec4 diffuse = texture2D(u_texture, v_texcoords);
	gl_FragColor = vec4(0.0, 0.0, 0.0, diffuse.a);
}