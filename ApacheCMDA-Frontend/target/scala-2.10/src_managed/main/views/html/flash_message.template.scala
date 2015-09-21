
package views.html

import play.templates._
import play.templates.TemplateMagic._

import play.api.templates._
import play.api.templates.PlayMagic._
import models._
import controllers._
import java.lang._
import java.util._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import play.api.i18n._
import play.core.j.PlayMagicForJava._
import play.mvc._
import play.data._
import play.api.data.Field
import play.mvc.Http.Context.Implicit._
import views.html._
/*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one or more		   *
 * contributor license agreements.  See the NOTICE file distributed with	   *
 * this work for additional information regarding copyright ownership.         *
 * The ASF licenses this file to You under the Apache License, Version 2.0     *
 * (the "License"); you may not use this file except in compliance with        *
 * the License.  You may obtain a copy of the License at                       *
 *                                                                             *
 *    http://www.apache.org/licenses/LICENSE-2.0                               *
 *																			   *
 * Unless required by applicable law or agreed to in writing, software         *
 * distributed under the License is distributed on an "AS IS" BASIS,           *
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.    *
 * See the License for the specific language governing permissions and         *
 * limitations under the License.											   *
 *******************************************************************************/
object flash_message extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template0[play.api.templates.HtmlFormat.Appendable] {

    /*******************************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one or more		   *
 * contributor license agreements.  See the NOTICE file distributed with	   *
 * this work for additional information regarding copyright ownership.         *
 * The ASF licenses this file to You under the Apache License, Version 2.0     *
 * (the "License"); you may not use this file except in compliance with        *
 * the License.  You may obtain a copy of the License at                       *
 *                                                                             *
 *    http://www.apache.org/licenses/LICENSE-2.0                               *
 *																			   *
 * Unless required by applicable law or agreed to in writing, software         *
 * distributed under the License is distributed on an "AS IS" BASIS,           *
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.    *
 * See the License for the specific language governing permissions and         *
 * limitations under the License.											   *
 *******************************************************************************/
    def apply():play.api.templates.HtmlFormat.Appendable = {
        _display_ {import helper._


Seq[Any](format.raw/*19.1*/("""
"""),_display_(Seq[Any](/*20.2*/if(flash.containsKey("error"))/*20.32*/ {_display_(Seq[Any](format.raw/*20.34*/("""
        
        <div class="alert alert-danger">
                <strong>Oops!</strong> """),_display_(Seq[Any](/*23.41*/flash/*23.46*/.get("error"))),format.raw/*23.59*/("""
                <a class="close" data-dismiss="alert">x</a>
        </div>
        
""")))})),format.raw/*27.2*/("""

"""),_display_(Seq[Any](/*29.2*/if(flash.containsKey("success"))/*29.34*/ {_display_(Seq[Any](format.raw/*29.36*/("""
        
        <div class="alert alert-success">
                """),_display_(Seq[Any](/*32.18*/flash/*32.23*/.get("success"))),format.raw/*32.38*/("""
                <a class="close" data-dismiss="alert">x</a>
        </div>
        
""")))})),format.raw/*36.2*/("""
"""))}
    }
    
    def render(): play.api.templates.HtmlFormat.Appendable = apply()
    
    def f:(() => play.api.templates.HtmlFormat.Appendable) = () => apply()
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Fri Sep 18 18:40:50 PDT 2015
                    SOURCE: /home/xingwei/SOC-Fall-2015/ApacheCMDA-Frontend/app/views/flash_message.scala.html
                    HASH: bc76ec21b6e0d1e7a199f2dd57bbafc261199186
                    MATRIX: 3274->1221|3311->1223|3350->1253|3390->1255|3517->1346|3531->1351|3566->1364|3683->1450|3721->1453|3762->1485|3802->1487|3907->1556|3921->1561|3958->1576|4075->1662
                    LINES: 60->19|61->20|61->20|61->20|64->23|64->23|64->23|68->27|70->29|70->29|70->29|73->32|73->32|73->32|77->36
                    -- GENERATED --
                */
            