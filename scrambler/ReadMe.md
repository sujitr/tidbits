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
Have to move towards 32 byte (256 bit) key based AES standard.