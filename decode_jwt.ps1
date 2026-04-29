$token = "eyJ0ZW5hbnRfaWQiOiJjZW50cm8tbWFkcmlkIiwiZmlyc3ROYW1lIjoiQW5hIiwicm9sZXMiOlsiUk9MRV9ESVJFQ1RPUiJdLCJpZCI6MSwiZmlyc3RTdXJuYW1lIjoiR2FyY8OtYSIsInN1YiI6ImFuYS5nYXJjaWFAY2VudHJvLW1hZHJpZC5jb20iLCJqdGkiOiIxMzlkOWUzNS0wMGM5LTRkYjItYjM5OS1jMmI3NzY5NDdlZmIiLCJpYXQiOjE3NzcyODgwMDEsImV4cCI6MTc3NzI5MTYwMX0"
$parts = $token.Split('.')
$payload = $parts[1]
# Add padding if needed
while ($payload.Length % 4 -ne 0) {
    $payload += "="
}
$bytes = [System.Convert]::FromBase64String($payload)
[System.Text.Encoding]::UTF8.GetString($bytes)