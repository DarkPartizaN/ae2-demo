#version 120

uniform sampler2D u_texture;
varying vec2 v_texcoords;

void main() {
	gl_FragColor = texture2D(u_texture, v_texcoords);
}