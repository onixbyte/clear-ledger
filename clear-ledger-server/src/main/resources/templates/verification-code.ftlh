<!DOCTYPE html>
<html lang="zh">
<head>
    <meta charset="UTF-8">
    <title>Verification Code</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        .container {
            width: 100%;
            max-width: 600px;
            margin: 50px auto;
            background-color: #ffffff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }

        .header {
            text-align: center;
            padding-bottom: 20px;
        }

        .code {
            font-family: MonoLisa, 'Deja Vu Sans Mono', Menlo, Consolas, monospace;
            font-size: 36px;
            font-weight: bold;
            color: #2c3e50;
            text-align: center;
            padding: 20px;
            background-color: #ecf0f1;
            border-radius: 5px;
            letter-spacing: 5px;
        }

        .footer {
            text-align: center;
            font-size: 12px;
            color: #7f8c8d;
            padding-top: 20px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="header">
        <h2>验证码</h2>
        <p>您好！以下是您的验证码，请在 5 分钟内使用：</p>
    </div>
    <div class="code">
        ${verificationCode}
    </div>
    <div class="footer">
        <p>此邮件为系统自动发送，请勿回复。</p>
        <p>© 2024-${.now?string('yyyy')} OnixByte Inc.</p>
    </div>
</div>
</body>
</html>