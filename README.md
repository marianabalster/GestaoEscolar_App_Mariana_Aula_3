# Gestão Escolar - Aula 3

**Aluna:** Mariana Balster  
**Curso:** Técnico em Desenvolvimento de Sistemas - SENAI PR

Projeto Java Swing da Aula 3: cadastro de espaços e recursos educacionais. O menu principal abre as duas telas. Os registros ficam em listas compartilhadas enquanto o programa estiver aberto; ao fechar, os dados são perdidos.

## Como executar

Requer **JDK 17 ou posterior**. Abra a pasta `GestaoEscolar_App` no VS Code com a extensão **Extension Pack for Java** e execute `src/br/senai/escola/app/Main.java` pelo botão **Run Java**.

Alternativamente, no terminal aberto dentro de `GestaoEscolar_App`:

```bash
mkdir -p out
javac -encoding UTF-8 -d out $(find src -name '*.java')
java -cp out br.senai.escola.app.Main
```

No Windows com PowerShell e a extensão Java, use preferencialmente **Run Java** no `Main.java`.

## Como usar

1. Abra **Cadastro de Espaços** e informe código, nome, tipo e capacidade inteira maior que zero. Exemplo: `LAB-01`, `Laboratório de informática`, `Laboratório`, `30`.
2. Abra **Cadastro de Recursos Educacionais** e informe patrimônio, descrição, categoria e quantidade inteira maior que zero. Escolha o espaço cadastrado. Exemplo: `PAT-032`, `Projetor`, `Equipamento`, `1`, `LAB-01`.
3. Se cadastrar um espaço enquanto a tela de recursos estiver aberta, clique em **Atualizar espaços**.
4. Confira os registros nas tabelas. Reabrir uma tela também mostra os cadastros existentes naquela execução.

## Regras verificáveis

- Código de espaço e patrimônio de recurso não podem se repetir, mesmo alterando letras maiúsculas/minúsculas.
- Código, nome, patrimônio e descrição são obrigatórios.
- Capacidade e quantidade devem ser números inteiros maiores que zero.
- Um recurso só pode ser cadastrado se houver um espaço selecionado.
- A associação é guardada como objeto `Espaco`, e a tabela mostra seu código.

## Arquivos

`src/br/senai/escola/app` inicia o programa; `models` define as classes de dados; `repository/BancoMemoria.java` mantém as listas; `views` contém o menu e os dois formulários.

## Entrega no Classroom

Depois de executar e conferir as duas telas, faça capturas de ambas **com cadastros realizados**, publique o projeto no seu repositório GitHub e envie o link, as duas capturas, seu nome completo e uma descrição do que concluiu. Os modelos `Aluno`, `Professor`, `Curso` e `Disciplina` estão presentes como estrutura básica; o foco funcional desta aula são espaços e recursos.

## Interface

As três telas usam um tema claro inspirado nos produtos Apple, com cartões arredondados, botões azuis e tabelas com maior espaçamento. A fonte é selecionada entre as famílias instaladas, nesta ordem: SF Pro Text, SF Pro Display, SF Pro, Segoe UI e SansSerif. Para usar SF Pro, ela precisa estar instalada no sistema antes de iniciar o programa; o projeto não inclui arquivos da fonte.
