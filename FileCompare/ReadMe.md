File Compare Utility
====================
This package is a utility library which provides ways to compare two files.
Specifically it has two methods to compare files by employing two different mechanisms.

## Normal byte match method
Its done by matching files byte by byte, reading from file system. This takes a bit more time as more IO is involved.

## Memory Mapped file comparison approach
In this approach both the files are mapped to memory and matched with byte sequence. This is usually faster as not much IO is involved.

## Future Improvements
Better test coverage and moving towards Junit 5 extension based test model.