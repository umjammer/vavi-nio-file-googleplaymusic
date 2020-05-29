[![Release](https://jitpack.io/v/umjammer/vavi-nio-file-googleplaymusic.svg)](https://jitpack.io/#umjammer/vavi-nio-file-googleplaymusic) [![Java CI with Maven](https://github.com/umjammer/vavi-nio-file-googleplaymusic/workflows/Java%20CI%20with%20Maven/badge.svg)](https://github.com/umjammer/vavi-nio-file-googleplaymusic/actions) [![Parent](https://img.shields.io/badge/Parent-vavi--apps--fuse-pink)](https://github.com/umjammer/vavi-apps-fuse)

# vavi-nio-file-googleplaymusic

A Java NIO FileSystem implementation for Google Play Music.

## Status

| fs        | list | upload | download | copy | move | rm | mkdir | cache | watch | library |
|-----------|------|--------|----------|------|------|----|-------|-------|-------|---------|
| googleplaymusic | ✅   | -    | ✅      | -   | -   | - | -    | -    | -     | [gplaymusic](https://github.com/umjammer/gplaymusic) |

## Install

https://jitpack.io/#umjammer/vavi-nio-file-googleplaymusic

## Usage

prepare 2 property files.

 * application credential

```shell
$ cat ${HOME}/.vavifuse/googleplaymusic.properties
googleplaymusic.androidId=your_android_id
```

 * user credential

```shell
$ cat ${HOME}/.vavifuse/credentials.properties
googleplaymusic.password.xxx@yyy.zzz=your_password
```

### Sample

https://github.com/umjammer/vavi-nio-file-googleplaymusic/blob/master/src/test/java/vavi/nio/file/googleplaymusic/GPMFileSystemProviderTest.java

## Test

```shell
$ cp local.properties.sample local.properties
$ vi local.properties
test.account=your_account@gmail.com
test.password=your_password
test.android_id=your_android_id
test.filter=*Your Artist Name*
$ mvn test
```
