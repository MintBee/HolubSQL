HolubSQL.jar
Version 1.0
Released 27 Sept., 2004

/*  (c) 2004 Allen I. Holub. All rights reserved.
 *
 *  This code may be used freely by yourself with the following
 *  restrictions:
 *
 *  o Your splash screen, about box, or equivalent, must include
 *    Allen Holub's name, copyright, and URL. For example:
 *
 *      This program contains Allen Holub's SQL package.<br>
 *      (c) 2005 Allen I. Holub. All Rights Reserved.<br>
 *              http://www.holub.com<br>
 *
 *    If your program does not run interactively, then the foregoing
 *    notice must appear in your documentation.
 *
 *  o You may not redistribute (or mirror) the source code.
 *
 *  o You must report any bugs that you find to me. Use the form at
 *    http://www.holub.com/company/contact.html or send email to
 *    allen@Holub.com.
 *
 *  o The software is supplied <em>as is</em>. Neither Allen Holub nor
 *    Holub Associates are responsible for any bugs (or any problems
 *    caused by bugs, including lost productivity or data)
 *    in any of this code.
 */


# 재고관리 프로그램
// 각 사람 이름 쓰기

주의: 만약 한글이 깨진다면 아래와 같이 인코딩을 지정해준다.
1. IDE 인코딩을 UTF-8로 변경 (Intellij의 경우 File -> Settings 에서 encoding을 검색해 모든 인코딩을 UTF-8로 변경해준다.)
2. Java VM 인코딩을 UTF-8로 변경
  다음과 같은 옵션을 추가해준다. (만약 Intellij를 사용한다면 shift-shift -> Edit Custom VM Options)
  * -Dfile.encoding=UTF-8
  * -Dconsole.encoding=UTF-8