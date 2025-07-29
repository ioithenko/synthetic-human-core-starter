# Synthetic Human Core Starter

Spring Boot Starter для управления командным центром андроидов Weyland-Yutani

## Функциональные модули

### 1. Модуль приема и исполнения команд
Ключевой компонент: `CommandProcessingService`

- CRITICAL команды выполняются немедленно
- COMMON команды помещаются в очередь (макс. размер настраивается)

### 2. Мониторинг занятости
Ключевой компонент: CommandMetricsService

Доступные метрики:

Размер очереди	 `/actuator/metrics/bishop.command.queue.size`

Команды по авторам	`/actuator/metrics/bishop.commands.by.author`	

### 3. Система аудита действий
Ключевые компоненты:
@WeylandWatchingYou - аннотация для аудита методов
AuditAspect - аспект обработки аудита
CompositeAuditPublisher - роутер сообщений

## Режимы работы (настраиваются в application.properties):
```
weyland.bishop.core.audit.mode=CONSOLE # или KAFKA/BOTH
weyland.bishop.core.audit.kafka.topic=audit-logs
```
