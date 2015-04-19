#version 120

uniform sampler2D u_scene, u_lightmap;

varying vec2 v_texcoords;

void main() {
	vec4 diffuse = texture2D(u_scene, v_texcoords);
    vec4 light = texture2D(u_lightmap, v_texcoords);

    vec3 final = diffuse.rgb * light.rgb;

    gl_FragColor = vec4(final, diffuse.a);
}