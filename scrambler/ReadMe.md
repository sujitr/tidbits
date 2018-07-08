Scrambler
=========
Its a Custom file encryptor & decryptor using AES encryption on top of Base64.
Helps you to move your files to/from anywhere, especially if you are behind a corporate firewall with email sniffers.


## General Feature
This module has features to convert any file to/from a lookalike of Base64 string after using both Base64 and AES (on a user provided 16 byte [128 bits] key). It generates the output in a file which contains that encrypted string in 76 character column separated lines, so that very long content can be stuffed in email's.

Upon receipt of the encrypted message in email, the decryptor program rearranges the 76 column wide lines in a single text and then decrypts it creating the original file which was sent in the email (of course with the user provided key which was used at encryption stage).

Using this you can bypass the email sniffers typically used at any organization checking for program codes, attachments and other sensitive stuffs in email.

Make sure your key strength is relatively strong and possible AES superiority over time.

## Future Improvements
- [x] Have to move towards 32 byte (256 bit) key based AES standard.
- [x] Use dependency injection to configure the key size(s) at runtime (128 bit or 256 bit)
- [ ] Use an intercative console input system to use the scrambler library with ease (Most probably with Java Console)
- [ ] Publish an API for those cases where access to console is not possible

#### References
1. [Veracode Article](https://www.veracode.com/blog/research/encryption-and-decryption-java-cryptography)
2. [Java 256-bit AES Password-Based Encryption](https://stackoverflow.com/questions/992019/java-256-bit-aes-password-based-encryption)
3. [Symmetric Encryption Best Practices](https://proandroiddev.com/security-best-practices-symmetric-encryption-with-aes-in-java-7616beaaade9)
4. [Encryption of big files with GCM](https://crypto.stackexchange.com/questions/20333/encryption-of-big-files-in-java-with-aes-gcm)