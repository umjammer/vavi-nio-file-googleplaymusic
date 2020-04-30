[![Release](https://jitpack.io/v/umjammer/vavi-nio-file-googleplaymusic.svg)](https://jitpack.io/#umjammer/vavi-nio-file-googleplaymusic) [![Actions Status](https://github.com/umjammer/vavi-nio-file-googleplaymusic/workflows/Java%20CI/badge.svg)](https://github.com/umjammer/vavi-nio-file-googleplaymusic/actions) [![Parent](https://img.shields.io/badge/Parent-vavi--apps--fuse-pink)](https://github.com/umjammer/vavi-apps-fuse)

# vavi-nio-file-googleplaymusic

A Java NIO FileSystem implementation for Google Play Music.

## Status

| fs        | list | upload | download | copy | move | rm | mkdir | cache | watch | library |
|-----------|------|--------|----------|------|------|----|-------|-------|-------|---------|
| googleplaymusic | ✅   | -    | ✅      | -   | -   | - | -    | -    | -     | [gplaymusic](https://github.com/umjammer/gplaymusic) |

## Install

https://jitpack.io/#umjammer/vavi-nio-file-googleplaymusic

## Usage

prepare a property file.

```shell
$ cat ${HOME}/.vavifuse/googleplaymusic.properties
googleplaymusic.androidId=your_android_id
googleplaymusic.password.xxx@yyy.zzz=your_password
```
