package br.com.tcc.Dominio;

import org.json.JSONObject;

import br.com.tcc.Constantes.Constantes;
import br.com.tcc.Utils.RequisicaoUtil;
import br.com.tcc.VO.UsuarioInstagramVO;

public class UsuarioInstagram {

	private UsuarioInstagramVO vo;
	
	public UsuarioInstagram() {
		vo = new UsuarioInstagramVO();
	}

	public UsuarioInstagramVO busqueUsuario() {
		try {
			
			String url = "https://api.instagram.com/v1/users/" 
						+ Constantes.CODIGO_GOIANIAINDICA 
						+ "/?access_token=" 
						+ Constantes.TOKEN;
			
			
			String response = RequisicaoUtil.getResponse(url);
			JSONObject jsonResposta = new JSONObject(response.toString());
			
			populeUsuario(jsonResposta);
			
		} catch (Exception e) {
			e.getMessage();
		}
		
		return vo;
	}
	
	private void populeUsuario(JSONObject json) {
		JSONObject data = RequisicaoUtil.busqueObjetoJson(json, "data");
		JSONObject counts = RequisicaoUtil.busqueObjetoJson(data, "counts");
		
		vo.setId(data.getString("id"));
		vo.setUsername(data.getString("username"));
		vo.setBio(data.getString("bio"));
		vo.setProfile_picture(data.getString("profile_picture"));
		vo.setFull_name(data.getString("full_name"));
		vo.setMedia(counts.getInt("media"));
		vo.setFollowed_by(counts.getInt("followed_by"));
		vo.setFollows(counts.getInt("follows"));
	}
}

