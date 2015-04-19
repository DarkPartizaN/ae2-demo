#version 120

uniform sampler2D u_texture;
varying vec2 tex_coords;

uniform float resolution;
uniform float radius;
uniform int dir;

vec4 vblur() {
	//this will be our RGBA sum
    vec4 sum = vec4(0.0);

    //our original texcoord for this fragment
    vec2 tc = tex_coords;

    float blur = radius / resolution;

    sum += texture2D(u_texture, vec2(tc.x - 4.0*blur, tc.y)) * 0.0162162162;
	sum += texture2D(u_texture, vec2(tc.x - 3.0*blur, tc.y)) * 0.0540540541;
	sum += texture2D(u_texture, vec2(tc.x - 2.0*blur, tc.y)) * 0.1216216216;
	sum += texture2D(u_texture, vec2(tc.x - blur, tc.y)) * 0.1945945946;
	sum += texture2D(u_texture, vec2(tc.x, tc.y)) * 0.2270270270;
	sum += texture2D(u_texture, vec2(tc.x + blur, tc.y)) * 0.1945945946;
	sum += texture2D(u_texture, vec2(tc.x + 2.0*blur, tc.y)) * 0.1216216216;
	sum += texture2D(u_texture, vec2(tc.x + 3.0*blur, tc.y)) * 0.0540540541;
	sum += texture2D(u_texture, vec2(tc.x + 4.0*blur, tc.y)) * 0.0162162162;

    return sum;
}

vec4 hblur() {
	//this will be our RGBA sum
    vec4 sum = vec4(0.0);

    //our original texcoord for this fragment
    vec2 tc = tex_coords;

    float blur = radius / resolution;

    sum += texture2D(u_texture, vec2(tc.x, tc.y - 4.0*blur)) * 0.0162162162;
	sum += texture2D(u_texture, vec2(tc.x, tc.y - 3.0*blur)) * 0.0540540541;
	sum += texture2D(u_texture, vec2(tc.x, tc.y - 2.0*blur)) * 0.1216216216;
	sum += texture2D(u_texture, vec2(tc.x, tc.y - blur)) * 0.1945945946;
	sum += texture2D(u_texture, vec2(tc.x, tc.y)) * 0.2270270270;
	sum += texture2D(u_texture, vec2(tc.x, tc.y + blur)) * 0.1945945946;
	sum += texture2D(u_texture, vec2(tc.x, tc.y + 2.0*blur)) * 0.1216216216;
	sum += texture2D(u_texture, vec2(tc.x, tc.y + 3.0*blur)) * 0.0540540541;
	sum += texture2D(u_texture, vec2(tc.x, tc.y + 4.0*blur)) * 0.0162162162;

    return sum;
}

void main() {
    if (bool(dir)) gl_FragColor = vblur();
	else gl_FragColor = hblur();
}