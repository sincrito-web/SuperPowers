# SuperPowers — Fabric 1.21.5

Projeto inicial de mod para Minecraft Java 1.21.5. A compilação acontece no GitHub Actions; não precisa instalar Java no computador local.

## Comandos no jogo
- `/sp help` — lista comandos.
- `/sp fly` — alterna voo.
- `/sp jump` — superpulo.
- `/sp haste` — pressa e resistência por 10 minutos.
- `/sp lightning` — invoca um raio alguns blocos à frente.
- `/sp boom` — cria uma explosão controlada à frente.

Use em um mundo de teste e faça backup do mundo antes de testar explosões. O código é um protótipo inicial; se a compilação acusar incompatibilidade de API, consulte o log do Actions e ajuste a linha indicada.

## Compilar sem instalar Java no PC
1. Entre em https://github.com/ e crie uma conta ou faça login.
2. Crie um repositório novo chamado `SuperPowers`.
3. Extraia este ZIP no computador.
4. Envie o conteúdo da pasta para o repositório (botão **Add file → Upload files**). Envie os arquivos e pastas internos, incluindo a pasta oculta `.github`.
5. Abra a aba **Actions**, selecione **Build SuperPowers mod** e clique em **Run workflow** se não iniciar automaticamente.
6. Quando terminar com sucesso, abra a execução e baixe o artefato **SuperPowers-1.21.5**.
7. Extraia o ZIP do artefato; o `.jar` estará dentro. Coloque-o na pasta `mods` junto com Fabric API compatível.

O GitHub Actions instala Java apenas no runner virtual, não no seu PC.
