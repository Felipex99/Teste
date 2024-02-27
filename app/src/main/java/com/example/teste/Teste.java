package com.example.teste;
import android.database.sqlite.SQLiteStatement;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import android.database.sqlite.SQLiteDatabase;

public class Teste extends AppCompatActivity{
    private LinearLayout framefilho,frameirmao;
    private AppCompatEditText ent_qtd, nome;
    private AppCompatTextView  pergunta;
    private boolean irmao_filho = true;
    private int child_count = 0,qtd_filhos,qtd_irmaos;
    private AppCompatButton add, rmv, salvar, trocar;



    private String filhos;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.teste_layout);
        SQLiteDatabase banco = openOrCreateDatabase("banco", MODE_PRIVATE,null);
        Toast.makeText(this, "Banco is open"+banco.isOpen(), Toast.LENGTH_SHORT).show();
        criarBanco();
        framefilho = findViewById(R.id.framefilho);
        frameirmao = findViewById(R.id.frameirmao);
        ent_qtd = findViewById(R.id.entrada_quantidade);
        pergunta = findViewById(R.id.pergunta);
        add = findViewById(R.id.add);
        trocar = findViewById(R.id.trocar);
        trocar.setText("IRMÃO");
        rmv = findViewById(R.id.rmv);
        salvar = findViewById(R.id.salvar);
        nome = findViewById(R.id.nome);
        filhos = ent_qtd.getText().toString();
        trocar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(irmao_filho){
                    trocar.setText("FILHOS");
                    pergunta.setText("DIGITE A QUANTIDADE DE FILHOS");
                    irmao_filho = false;
                }else{
                    trocar.setText("IRMÃO");
                    pergunta.setText("DIGITE A QUANTIDADE DE IRMÃOS");
                    irmao_filho = true;
                }
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(irmao_filho){
                    child_count = Integer.parseInt(ent_qtd.getText().toString());
                    qtd_filhos = child_count;
                    for(int i = 0;i<child_count;i++){
                        addBrother();
                    }
                }else{
                    qtd_irmaos = Integer.parseInt(ent_qtd.getText().toString());
                    child_count = Integer.parseInt(ent_qtd.getText().toString());
                    for(int i = 0;i<child_count;i++){
                        addChild();
                    }
                }
                Toast.makeText(Teste.this, "IRMÃO ADICIONADO", Toast.LENGTH_SHORT).show();

            }
        });
        rmv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                child_count = 0;
                framefilho.removeAllViews();
                frameirmao.removeAllViews();
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
        View childView = LayoutInflater.from(this).inflate(R.layout.child_filho, null);
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

        framefilho.addView(childView);
    }

    private void addBrother(){
        View childBrother = LayoutInflater.from(this).inflate(R.layout.child_irmao,null);
        int childId = View.generateViewId();

        childBrother.setId(childId);

        AppCompatTextView texto_nome = childBrother.findViewById(R.id.texto_nome);
        AppCompatTextView texto_idade = childBrother.findViewById(R.id.texto_idade);
        AppCompatEditText nome_irmao = childBrother.findViewById(R.id.nome_irmao);
        AppCompatEditText idade_irmao = childBrother.findViewById(R.id.idade_irmao);

        String Snome = texto_nome.getText().toString()+ childId;
        String Sidade = texto_idade.getText().toString()+childId;
        texto_nome.setText(Snome);
        texto_idade.setText(Sidade);
        frameirmao.addView(childBrother);
    }

    public void criarBanco(){
        try{
            SQLiteDatabase banco = openOrCreateDatabase("banco",MODE_PRIVATE,null);
//            banco.execSQL("DROP TABLE IF EXISTS filho ");
//            banco.execSQL("DROP TABLE IF EXISTS irmao ");
//            banco.execSQL("DROP TABLE IF EXISTS pai");
            banco.execSQL("CREATE TABLE IF NOT EXISTS pai(" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "NOME VARCHAR)");
            banco.execSQL("CREATE TABLE IF NOT EXISTS filho(" +
                    "ID INTEGER  PRIMARY KEY AUTOINCREMENT," +
                    "ID_PAI INTEGER," +
                    "NOME VARCHAR," +
                    "IDADE INTEGER," +
                    "FOREIGN KEY (ID_PAI) REFERENCES pai(ID))");
            banco.execSQL("CREATE TABLE IF NOT EXISTS irmao(" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "ID_PAI INTEGER," +
                    "NOME VARCHAR," +
                    "IDADE INTEGER," +
                    "FOREIGN KEY (ID_PAI) REFERENCES pai(ID))");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void inserir(){
        try{
            SQLiteDatabase banco = openOrCreateDatabase("banco", MODE_PRIVATE,null);

            String query = "INSERT INTO pai(NOME)VALUES(?)";
            SQLiteStatement stmt = banco.compileStatement(query);

            if("NOME"!=null){
                stmt.bindString(1, nome.getText().toString());
            }else{
                stmt.bindString(1,"");
            }
            long id  = stmt.executeInsert();

            Toast.makeText(this, "IDPAI: "+id, Toast.LENGTH_SHORT).show();

            query = "INSERT INTO filho(NOME, IDADE, ID_PAI) VALUES (?, ?, ?)";
            stmt = banco.compileStatement(query);
            if(qtd_filhos>0){
                for(int i = 0 ;i<framefilho.getChildCount();i++){
                    View childView = framefilho.getChildAt(i);
                    AppCompatEditText nome = childView.findViewById(R.id.nome);
                    AppCompatEditText idade = childView.findViewById(R.id.idade);
                    if("NOME" != null){
                        stmt.bindString(1, nome.getText().toString());
                    }else{
                        stmt.bindString(1, "");
                    }
                    if("IDADE" != null){
                        stmt.bindString(2,idade.getText().toString());
                    }else{
                        stmt.bindString(2,"");
                    }
                    if("ID_PAI" != null){
                        stmt.bindString(3,String.valueOf(id));
                    }else{
                        stmt.bindString(3,"");
                    }
                    stmt.executeInsert();
                }
            }
            query = "INSERT INTO irmao(NOME, IDADE, ID_PAI) VALUES (?, ?, ?)";
            stmt = banco.compileStatement(query);
            if(qtd_irmaos>0){
                for(int i = 0 ;i<frameirmao.getChildCount();i++){
                    View childView = frameirmao.getChildAt(i);
                    AppCompatEditText nome = childView.findViewById(R.id.nome_irmao);
                    AppCompatEditText idade = childView.findViewById(R.id.idade_irmao);

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
                    if("ID_PAI"!=null){
                        stmt.bindString(3, String.valueOf(id));
                    }else{
                        stmt.bindString(3,"");
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
