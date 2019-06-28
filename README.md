# SSH Utils Example Library

[![pipeline status](https://gitlab.com/paulushc/sshutil/badges/master/pipeline.svg)](https://gitlab.com/paulushc/sshutil/commits/master)
[![coverage report](https://gitlab.com/paulushc/sshutil/badges/master/coverage.svg)](https://gitlab.com/paulushc/sshutil/commits/master)
![Sonar Coverage](https://img.shields.io/sonar/https/sonarcloud.io/paulushcgcj_sshutil/coverage.svg)
[![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=paulushcgcj_sshutil&metric=alert_status)](https://sonarcloud.io/dashboard?id=paulushcgcj_sshutil)


[![GitHub license](https://img.shields.io/github/license/paulushcgcj/sshutil.svg)](https://github.com/paulushcgcj/sshutil/blob/master/LICENSE.md)
![Maven Central](https://img.shields.io/maven-central/v/io.github.paulushcgcj/sshutil.svg)
![Bintray](https://img.shields.io/bintray/v/paulushc/io.github.paulushcgcj/sshutil.svg)
![GitHub release](https://img.shields.io/github/release/paulushcgcj/sshutil.svg)


This library is intended to be used as an example when using jsch. It's part of the article about SSH and Java that I wrote on Medium on [en-US](https://medium.com/@paulushc/secure-shell-ssh-and-java-3bb380050d4b) and  [pt-BR](https://medium.com/@paulushc/secure-shell-ssh-e-java-d47c10996256). You can also find it on [my blog](https://paulushcgcj.github.io/article/tech/java/ssh/2016/11/09/secure-shell-and-java.html)

The idea is only to show how can you build up a library that suits your needs when connecting to remote servers, be it for upload/download or to execute commands. If you find this lib useful, you can contribute to it, or even use it, as it will be released on maven central and bintray as well.

This code is just a draft example of how to use [jsch](http://www.jcraft.com/jsch/) inside your project, so don't expect nothing complex or ultra secure as this is just a draft example. If you want more complex examples, check out [jsch examples page](http://www.jcraft.com/jsch/examples/)