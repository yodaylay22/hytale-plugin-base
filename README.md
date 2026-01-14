# Hytale Plugin Mini Framework

Base simples para criar **comandos** e **eventos** no Hytale sem registro manual.
O objetivo é **produtividade**, **padrão** e **menos erro humano**, sem esconder o que está acontecendo.

---

## ⚠️ AVISO IMPORTANTE

> **DELETE os comandos e eventos de exemplo antes de usar em produção.**
>
> * `ExampleCommand`
> * `ExampleEvent` / `PlayerReadyListener`
>
> Eles existem **apenas como referência**.

---

## 📁 Estrutura esperada

```text
src/main/java/seu/pacote/base
│
├─ SeuPlugin.java
│
├─ commands/
│   └─ SeusComandos.java
│
├─ events/
│   └─ SeusEventos.java
│
└─ framework/
    ├─ command/
    ├─ event/
    └─ scan/
```

---

## 🚀 Como funciona (resumo rápido)

No `setup()` do plugin:

1. O framework **escaneia o JAR do plugin**
2. Encontra classes anotadas
3. Registra comandos e eventos automaticamente
4. Se algo estiver errado → **falha no startup**

Nada é registrado manualmente.

---

## 🔌 Plugin principal

```java
@Override
protected void setup() {

    ClassLoader pluginClassLoader = this.getClass().getClassLoader();

    Set<Class<?>> classes = ClassScanner.scan(
        pluginClassLoader,
        "dev.hytalemodding" // package base
    );

    CommandAutoRegistrar.register(this, classes);
    EventAutoRegistrar.register(this, classes);
}
```

---

## 🧱 Criando comandos

### Regras

* Estender `BaseCommand`
* Ter construtor vazio
* Usar `@CommandDef`

### Exemplo: comando `/lobby`

```java
@CommandDef(
    name = "lobby",
    description = "Leva o jogador para o spawn"
)
public class LobbyCommand extends BaseCommand {

    public LobbyCommand() {
        super("lobby", "Leva o jogador para o spawn");
    }

    @Override
    protected void executeSync(@Nonnull CommandContext context) {

        if (!(context.getSender() instanceof Player player)) {
            return;
        }

        player.move(
            player.getWorld().getSpawnPosition()
        );
    }
}
```

➡️ Não é necessário registrar esse comando em lugar nenhum.

---

## 🎯 Criando eventos

### Regras

* Implementar `EventListener<T>`
* Usar `@Listen(event = X.class)`
* Lógica sempre no método `handle`

### Exemplo: `PlayerReadyEvent`

```java
@Listen(event = PlayerReadyEvent.class)
public class PlayerReadyListener implements EventListener<PlayerReadyEvent> {

    @Override
    public void handle(PlayerReadyEvent event) {

        Player player = event.getPlayer();

        player.sendMessage(
            Message.raw("Bem-vindo ao servidor!")
        );
    }
}
```

➡️ O evento é registrado automaticamente no startup.

---

## ❌ O que NÃO fazer

* ❌ Registrar comandos manualmente
* ❌ Registrar eventos manualmente
* ❌ Usar lambda para eventos
* ❌ Esconder exceptions
* ❌ Escanear classpath inteiro

Se quebrar, **quebra no startup**. Isso é intencional.

---

## ✅ O que esse framework entrega

* Auto-discovery real (funciona em JAR)
* Padrão único de comandos e eventos
* Menos boilerplate
* Código previsível
* Fácil de debugar

---

## 🧠 Observação final

Esse framework **não tenta recriar Laravel ou Spring**.
Ele resolve **um problema específico** de plugins Hytale:

> produtividade sem perder controle.

Se quiser evoluir:

* permissions
* subcommands
* core plugin compartilhado
* logs de auto-registro

Tudo isso pode ser construído em cima dessa base.
