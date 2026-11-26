# 🏀 NBA App

Aplicativo Android moderno para visualização de times, partidas e jogadores da **NBA**, desenvolvido com **Jetpack Compose**, **Material 3** e **Clean Architecture**. Os dados são consumidos em tempo real da [Balldontlie API](https://www.balldontlie.io/).

---

## 📱 Funcionalidades

- **Lista de Times (Home)**:
  - Visualização de todas as franquias da NBA com conferência, divisão e cidade.
  - Ordenação dinâmica dos times por **Nome**, **Cidade** ou **Conferência**.
- **Jogos do Time (Bottom Sheet)**:
  - Ao selecionar um time, exibe os últimos jogos com placar detalhado (mandante vs visitante).
- **Lista de Jogadores**:
  - Exibição de jogadores e seus respectivos times atuais.
- **Navegação Intuitiva**:
  - Barra de navegação inferior (*Bottom Navigation*) para alternar facilmente entre Times e Jogadores.
- **Tratamento de Estados e Erros**:
  - Estados de tela gerenciados de forma reativa (`Loading`, `Success`, `Error`).
  - Cards de erro customizados com opção de tentar novamente (*retry*).

---

## 🛠️ Tecnologias e Bibliotecas

- **Linguagem**: [Kotlin](https://kotlinlang.org/)
- **UI Toolkit**: [Jetpack Compose](https://developer.android.com/jetpack/compose) com [Material 3](https://m3.material.io/)
- **Gerenciamento de Estado & Reatividade**:
  - `ViewModel` & `StateFlow` (Lifecycle Runtime KTX)
  - `collectAsStateWithLifecycle`
  - Kotlin Coroutines & Flow
- **Comunicação de Rede & Serialização**:
  - [Ktor Client](https://ktor.io/) (OkHttp Engine, Content Negotiation, Logging)
  - [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization) (JSON)
- **Design de Arquitetura**:
  - Clean Architecture (Separação em camadas: Core, Domain, Data e Presentation)
  - Padrão MVVM / MVI-like State Pattern
- **Testes**:
  - [JUnit 4](https://junit.org/junit4/)
  - [Kotlinx Coroutines Test](https://github.com/Kotlin/kotlinx.coroutines/tree/master/kotlinx-coroutines-test) (TestDispatchers)
  - Ktor Client Mock Engine (Simulação de requisições HTTP)

---

## 🏛️ Estrutura do Projeto

O projeto segue as diretrizes do **Clean Architecture**:

```text
app/src/main/java/com/bumba/nbaapp/
├── core/
│   └── presentation/          # UiState genérico, tratamento de erros e componentes comuns
├── data/
│   ├── datasource/            # NbaRemoteDataSource, cliente Ktor e DTOs da API
│   └── repository/            # Implementação do repositório (NbaRepositoryImpl) e Mappers
├── domain/
│   ├── model/                 # Modelos de domínio (Team, Player, Game)
│   └── repository/            # Interfaces de repositório (NbaRepository)
└── presentation/
    ├── components/            # Componentes reutilizáveis (MainBottomBar, TeamBottomSheet)
    ├── tabs/                  # Telas principais (HomeScreen, PlayerScreen)
    ├── theme/                 # Cores, Tipografia e Tema Material 3
    ├── MainActivity.kt        # Entry point e controle de navegação
    └── MainViewModel.kt       # ViewModel responsável pelo estado da UI
```

---

## 🚀 Como Executar o Projeto

### Pré-requisitos
- **Android Studio** Ladybug / Meerkat ou superior.
- **JDK 11** ou superior configurado.
- Dispositivo Android físico ou emulador com **API 24+**.
- Uma chave de API gratuita da [Balldontlie API](https://www.balldontlie.io/).

### Passo a Passo

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/seu-usuario/nba-app.git
   cd nba-app
   ```

2. **Configure a chave da API:**
   No diretório raiz do projeto, crie ou edite o arquivo `local.properties` e adicione a sua chave da Balldontlie API:
   ```properties
   API_KEY=sua_chave_aqui
   ```
   > 💡 *Nota*: A chave é carregada automaticamente pelo Gradle e injetada no `BuildConfig.API_KEY`.

3. **Sincronize e Execute:**
   - Abra o projeto no Android Studio.
   - Aguarde o término do *Gradle Sync*.
   - Selecione o dispositivo de destino e clique em **Run** (`Shift + F10`).

---

## 🧪 Executando os Testes

O projeto conta com cobertura de testes unitários para as camadas de dados, domínio e apresentação:

```bash
# Executar todos os testes unitários via Gradle Wrapper
./gradlew test
```

Os testes incluem:
- **`NbaMapperTest`**: Validação da transformação de DTOs para modelos de domínio.
- **`NbaRepositoryImplTest`**: Testes de integração da camada de repositório usando `MockEngine` do Ktor.
- **`MainViewModelTest`**: Testes de emissão de estados (`Loading`, `Success`, `Error`), ordenação e controle de coroutines com dispatcher de teste.
