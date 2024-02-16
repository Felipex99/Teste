package com.example.teste;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

public class Teste extends AppCompatActivity{
    private LinearLayout frame;
    private AppCompatEditText qtd_filhos;
    private int child_count;
    private AppCompatButton add;

    private String filhos;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teste_layout);
        frame = findViewById(R.id.frameLayout);
        qtd_filhos = findViewById(R.id.qtd_filhos);
        add = findViewById(R.id.add);
        filhos = qtd_filhos.getText().toString();
        qtd_filhos.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Toast.makeText(Teste.this, "Key code:"+keyCode+" | KEY EVENT: "+event, Toast.LENGTH_LONG).show();
                Log.d("KEY CODE","Key code:"+keyCode+" | KEY EVENT: "+event);
                return false;
            }
        });
        if (!(qtd_filhos.getText().toString()!=null)){
            add.setText("aaaaaaaaaaaaaaaaaaaaaaa");
            Toast.makeText(this, "Não tá vazio", Toast.LENGTH_SHORT).show();
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Teste.this, "Clicado", Toast.LENGTH_SHORT).show();
                addChild();
            }
        });
    }























    private void addChild() {
        child_count++;
        AppCompatTextView novo_texto = new AppCompatTextView(this);
        LinearLayout linearLayout = new LinearLayout(this);

        View childView = LayoutInflater.from(this).inflate(R.layout.child, null);
        int childId = View.generateViewId();
        childView.setId(childId);
        frame.addView(childView);
        AppCompatTextView texto_nome = childView.findViewById(R.id.texto_nome);
        AppCompatTextView texto_idade = childView.findViewById(R.id.texto_idade);
        String Snome = texto_nome.getText().toString() + childId;
        texto_nome.setText(Snome);
        String Sidade = texto_idade.getText().toString() + childId;
        texto_idade.setText(Sidade);
        AppCompatEditText idade = childView.findViewById(R.id.idade);
        AppCompatEditText nome = childView.findViewById(R.id.nome);

    }
    //criarFrames(frame);
//        frame.addView(linearLayout);
//        frame.addView(novo_texto);
//        novo_texto.setId(child_count);
//        novo_texto.setText("INFORMAÇÃO DO FILHO " + child_count);
//        novo_texto.setTextSize(20);
//        novo_texto.setLeft(40);
//        novo_texto.setTop(50);
//        LayoutInflater inflater = LayoutInflater.from(this);
//        View childView = inflater.inflate(R.layout.child,null);
//        child_count++;
//        AppCompatTextView texto = childView.findViewById(R.id.texto);
//        AppCompatEditText idade = childView.findViewById(R.id.idade);
//        texto.setId(View.generateViewId());
//        idade.setId(View.generateViewId());
//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
//            FrameLayout.LayoutParams.MATCH_PARENT,
//            FrameLayout.LayoutParams.WRAP_CONTENT
//        );
//        layoutParams.topMargin = child_count*getResources().getDimensionPixelSize(R.dimen.margin_)
//    public void criarFrames(View view) {
//        String numFilhosStr = qtd_filhos.getText().toString();
//        if (!numFilhosStr.isEmpty()) {
//            int numFilhos = Integer.parseInt(numFilhosStr);
//            for (int i = 1; i <= numFilhos; i++) {
//                FrameLayout novoFrame = criarFrameParaFilho(i);
//                frame.addView(novoFrame);
//            }
//        }
//    }
//
//    private FrameLayout criarFrameParaFilho(int numeroFilho) {
//        FrameLayout novoFrame = new FrameLayout(this);
//        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT
//        );
//        novoFrame.setLayoutParams(layoutParams);

        //AppCompatEditText nomeEditText = new AppCompatEditText(this);
        //nomeEditText.setHint("Nome do Filho " + numeroFilho);
        // Adicione outros atributos ao EditText, como tamanho, margens, etc.

        //AppCompatEditText idadeEditText = new AppCompatEditText(this);
        //idadeEditText.setHint("Idade do Filho " + numeroFilho);
        // Adicione outros atributos ao EditText, como tamanho, margens, etc.

        //novoFrame.addView(nomeEditText);
        //novoFrame.addView(idadeEditText);
//        novoFrame.addView(frame);
//        return novoFrame;
//    }
}
