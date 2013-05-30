Проект содержит сервис со следующим описанием:

```java
<service
            android:exported="true"
            android:name="HotService"
            android:permission="com.example.hotcode.SERVICES">
            <intent-filter>
                <action android:name="edu.hotcode.write"/>
                <data android:scheme="content" android:mimeType="*/*" />
            </intent-filter>
</service>
```
Сервис скачивает данные с интернета и записывает их по URI переданному в интенте.

Данное приложение нужно установить перед выполнением следующего задания.

Для запуска сервиса нужно использовать метод **context.startService(intent)**, со следующими параметрами интента:


- **data** - URI по которому сервис запишет скачанные с сервера данные
- **action** - "edu.hotcode.write"

**PS.** Этот проект править не нужно - он только предоставляет функционал
