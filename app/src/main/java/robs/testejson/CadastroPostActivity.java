package robs.testejson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import edu.fae.util.http.JsonResult;
import edu.fae.util.http.JsonResultHandler;
import edu.fae.util.http.PostAsyncTask;
import edu.fae.util.http.PostRequest;

public class CadastroPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_post);
    }

    /**
     * Chamado quando o usuário clica no botão Cadastrar
     * @param view
     */
    public void cadastrar(View view) {
        //Capturando os dados digitados pelo usuário no formulário
        EditText txtNome = (EditText) findViewById(R.id.txtNome);
        EditText txtEmail = (EditText) findViewById(R.id.txtEmail);
        EditText txtSenha = (EditText) findViewById(R.id.txtSenha);

        String nome = txtNome.getText().toString();
        String email = txtEmail.getText().toString();
        String senha = txtSenha.getText().toString();

        //Classe que faz a chamada via POST do JSON
        PostAsyncTask postService = new PostAsyncTask(new JsonResultHandler() {
            @Override
            public void onJsonResult(JsonResult result) {
                cadastroResult(result);
            }
        }, this);

        //Dados a serem enviados para o post
        //No construtor vai a URL do servidor
        PostRequest req = new PostRequest("http://robs-socialr.rhcloud.com/register/save.json");
        //Parâmetros enviados para o post
        req.addParam("nome", nome);
        req.addParam("email", email);
        req.addParam("senha", senha);

        //Executa a chamada POST
        postService.execute(req);
    }

    /**
     * Método que é chamado quando o serviço de POST JSON retornar
     * @param result
     */
    private void cadastroResult(JsonResult result) {
        //Verifica se teve sucesso no retorno e se veio um objeto json de resposta
        if(result.hasSuccess() && result.hasJsonObject()) {
            Toast.makeText(this, "Cadastro efetuado com sucesso! " + result.getJsonObject().toString(), Toast.LENGTH_LONG).show();
        }else if(result.hasError()){ //Verifica se teve erro
            //Apresenta a tela de erro para o usuário
            Toast.makeText(this, "Erro: " + result.getErrorMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
