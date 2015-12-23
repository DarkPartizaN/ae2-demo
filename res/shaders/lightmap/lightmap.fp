#version 120

uniform sampler2D u_scene, u_lightmap, u_shadowmap;

varying vec2 v_texcoords;

void main() {
	vec4 diffuse = texture2D(u_scene, v_texcoords);
    vec4 light = texture2D(u_lightmap, v_texcoords);
	vec4 shadow = texture2D(u_shadowmap, v_texcoords);
	vec3 color = diffuse.rgb * light.rgb * shadow.rgb;
	vec4 final;

	final = vec4(color, 1.0);

    gl_FragColor = final;
}