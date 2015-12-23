#version 120

uniform sampler2D u_texture;
uniform vec4 u_color;

varying vec2 v_texcoords;

void main() {
	vec4 diffuse = texture2D(u_texture, v_texcoords);

	gl_FragColor = vec4(u_color.r, u_color.g, u_color.b, (diffuse.a * u_color.a));
}