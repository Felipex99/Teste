package com.example.teste;
import android.database.sqlite.SQLiteStatement;
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
import android.database.sqlite.SQLiteDatabase;

public class Teste extends AppCompatActivity{
    private LinearLayout frame;
    private AppCompatEditText qtd_filhos,qtd_irmaos;
    private int child_count = 0;
    private AppCompatButton add, rmv,salvar;

    private String filhos;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teste_layout);
        criarBanco();
        frame = findViewById(R.id.frameLayout);
        qtd_filhos = findViewById(R.id.qtd_filhos);
        add = findViewById(R.id.add);
        rmv = findViewById(R.id.rmv);
        salvar = findViewById(R.id.salvar);
        filhos = qtd_filhos.getText().toString();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Teste.this, "Clicado", Toast.LENGTH_SHORT).show();
                child_count = Integer.parseInt(qtd_filhos.getText().toString());
                for(int i = 0;i<child_count;i++){
                    addChild();
                }
            }
        });
        rmv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                child_count = 0;
                frame.removeAllViews();
                Toast.makeText(Teste.this, "Quantidade de filhos"+frame.getChildCount(), Toast.LENGTH_SHORT).show();
            }
        });

        salvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inserir();
            }
        });
    }


    private void addChild() {
        AppCompatTextView novo_texto = new AppCompatTextView(this);
        LinearLayout linearLayout = new LinearLayout(this);

        View childView = LayoutInflater.from(this).inflate(R.layout.child, null);
        int childId = View.generateViewId();

        childView.setId(childId);

        AppCompatTextView texto_nome = childView.findViewById(R.id.texto_nome);
        AppCompatTextView texto_idade = childView.findViewById(R.id.texto_idade);

        String Snome = texto_nome.getText().toString() + childId;
        texto_nome.setText(Snome);
        String Sidade = texto_idade.getText().toString() + childId;
        texto_idade.setText(Sidade);
        AppCompatEditText idade = childView.findViewById(R.id.idade);
        AppCompatEditText nome = childView.findViewById(R.id.nome);

        frame.addView(childView);
    }

    public void criarBanco(){
        try{
            SQLiteDatabase banco = openOrCreateDatabase("banco",MODE_PRIVATE,null);
            banco.execSQL("CREATE TABLE IF NOT EXISTS filho(" +
                    "ID INTEGER  PRIMARY KEY AUTOINCREMENT," +
                    "NOME VARCHAR," +
                    "IDADE INTEGER)");
            banco.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void inserir(){
        try{
            SQLiteDatabase banco = openOrCreateDatabase("banco", MODE_PRIVATE,null);
            String query = "INSERT INTO filho(NOME, IDADE) VALUES (?, ?)";
            if(Integer.parseInt(qtd_filhos.getText().toString())>0){
                for(int i = 0 ;i<frame.getChildCount();i++){
                    SQLiteStatement stmt = banco.compileStatement(query);
                    View childView = frame.getChildAt(i);
                    AppCompatEditText nome = childView.findViewById(R.id.nome);
                    AppCompatEditText idade = childView.findViewById(R.id.idade);

                    if("NOME"!=null){
                        stmt.bindString(1, nome.getText().toString());
                    }else{
                        stmt.bindString(1, "");
                    }
                    if("IDADE"!=null){
                        stmt.bindString(2,idade.getText().toString());
                    }else{
                        stmt.bindString(2,"");
                    }
                    stmt.executeInsert();
                }
            }
            Toast.makeText(this, "DADOS INSERIDOS COM SUCESSO", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
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
