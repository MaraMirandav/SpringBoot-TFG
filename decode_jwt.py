#!/usr/bin/env python3
import base64
import json
import sys

# Read token from file if provided, otherwise ask
if len(sys.argv) > 1:
    token = sys.argv[1]
else:
    token = input("Enter JWT token: ").strip()

print(f"Token has {token.count('.')} dots")

parts = token.split('.')
print(f"Parts: {len(parts)}")

if len(parts) < 2:
    print("Invalid token - no dots found")
    sys.exit(1)

payload = parts[1].replace('-', '+').replace('_', '/')

# Add padding
padding = 4 - (len(payload) % 4)
payload += '=' * padding

decoded = base64.b64decode(payload)
print(decoded.decode())