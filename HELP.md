# Help

## Generate GPG Key
```
gpg --full-generate-key
```

## Using GPG signing in maven with github action

- find by email
```
gpg --list-secret-keys user@example.com
```

- update keyserver
```
gpg --keyserver hkp://pool.sks-keyservers.net --send-keys "$KEY_ID"
gpg --send-keys --keyserver keyserver.ubuntu.com "$KEY_ID"
```

- export base64
```
gpg --export-secret-keys YOUR_ID_HERE | base64 > private.key
```
- add secrets in github actions and configure
```
- name: Configure GPG Key
  run: |
    mkdir -p ~/.gnupg/
    printf "$GPG_SIGNING_KEY" | base64 --decode > ~/.gnupg/private.key
    gpg --import ~/.gnupg/private.key
  env:
    GPG_SIGNING_KEY: ${{ secrets.GPG_SIGNING_KEY }}
```
