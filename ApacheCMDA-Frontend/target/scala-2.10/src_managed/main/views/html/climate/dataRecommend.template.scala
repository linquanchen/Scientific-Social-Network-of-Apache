
package views.html.climate

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
object dataRecommend extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template1[String,play.api.templates.HtmlFormat.Appendable] {

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
    def apply/*18.2*/(jsonData: String):play.api.templates.HtmlFormat.Appendable = {
        _display_ {import helper._

def /*31.2*/scripts/*31.9*/:play.api.templates.HtmlFormat.Appendable = {_display_(

Seq[Any](format.raw/*31.13*/("""
    <script src='"""),_display_(Seq[Any](/*32.19*/routes/*32.25*/.Assets.at("javascripts/edit_button.js"))),format.raw/*32.65*/("""'></script>
    <script src='"""),_display_(Seq[Any](/*33.19*/routes/*33.25*/.Assets.at("javascripts/livefilter.js"))),format.raw/*33.64*/("""'></script>
    <script type="text/javascript" src='"""),_display_(Seq[Any](/*34.42*/routes/*34.48*/.Assets.at("javascripts/jquery-1.9.0.min.js"))),format.raw/*34.93*/("""'></script>
""")))};
Seq[Any](format.raw/*18.20*/("""
"""),format.raw/*20.1*/("""
<link rel="stylesheet"
    href='"""),_display_(Seq[Any](/*22.12*/routes/*22.18*/.Assets.at("stylesheets/custom_recommend.css"))),format.raw/*22.64*/("""'>

<link rel="stylesheet"
  href="https://cdnjs.cloudflare.com/ajax/libs/vis/3.11.0/vis.min.css"
>

<link rel="stylesheet"
    href='"""),_display_(Seq[Any](/*29.12*/routes/*29.18*/.Assets.at("stylesheets/livefitler.css"))),format.raw/*29.58*/("""'>
    
"""),format.raw/*35.2*/("""

"""),_display_(Seq[Any](/*37.2*/main("Service Logs", scripts)/*37.31*/{_display_(Seq[Any](format.raw/*37.32*/("""
    
    """),_display_(Seq[Any](/*39.6*/flash_message())),format.raw/*39.21*/("""   

  <div id="jsonData" style="display: none;">"""),_display_(Seq[Any](/*41.46*/jsonData)),format.raw/*41.54*/("""</div>
  <div id="graphText">
    <div class="row">
      <div class="col-lg-9" id="testGraph">
      </div>
      <div class="col-lg-3" id="testText">
      </div>
    </div>
  </div>


  <script src="https://cdnjs.cloudflare.com/ajax/libs/vis/3.11.0/vis.min.js"></script>
  <script type="text/javascript">
      
      var jsonString = $('#jsonData').text();
      var data = JSON.parse(jsonString);
      var container = document.getElementById('testGraph');

      var options = """),format.raw/*59.21*/("""{"""),format.raw/*59.22*/("""
        stabilize:false,
        edges: """),format.raw/*61.16*/("""{"""),format.raw/*61.17*/("""
          color: """),format.raw/*62.18*/("""{"""),format.raw/*62.19*/("""
            color: "gray",
            highlight: "gray",
          """),format.raw/*65.11*/("""}"""),format.raw/*65.12*/(""",
        """),format.raw/*66.9*/("""}"""),format.raw/*66.10*/(""",
        nodes: """),format.raw/*67.16*/("""{"""),format.raw/*67.17*/("""
          shape: 'dot',
          radiusMin: 10,
          radiusMax: 30,
        """),format.raw/*71.9*/("""}"""),format.raw/*71.10*/(""",
        groups: """),format.raw/*72.17*/("""{"""),format.raw/*72.18*/("""
          user: """),format.raw/*73.17*/("""{"""),format.raw/*73.18*/("""
            color:"#F2545A",
            shape:"star",
          """),format.raw/*76.11*/("""}"""),format.raw/*76.12*/(""",
          feature: """),format.raw/*77.20*/("""{"""),format.raw/*77.21*/("""
            shape:"triangle",
            color:"#EC8F93",
          """),format.raw/*80.11*/("""}"""),format.raw/*80.12*/(""",
          service: """),format.raw/*81.20*/("""{"""),format.raw/*81.21*/("""
            color:"#EF777C",
            shape:"dot",
          """),format.raw/*84.11*/("""}"""),format.raw/*84.12*/(""",
          data: """),format.raw/*85.17*/("""{"""),format.raw/*85.18*/("""
            color:"#F46369",
            shape:"square",
          """),format.raw/*88.11*/("""}"""),format.raw/*88.12*/("""
        """),format.raw/*89.9*/("""}"""),format.raw/*89.10*/(""",
        tooltip: """),format.raw/*90.18*/("""{"""),format.raw/*90.19*/("""
          delay: 300,
          fontColor: "black",
          fontSize: 14,
          fontFace: "verdana",
          color: """),format.raw/*95.18*/("""{"""),format.raw/*95.19*/("""
            border: "#666",
            background: "#FFFFC6"
          """),format.raw/*98.11*/("""}"""),format.raw/*98.12*/("""
        """),format.raw/*99.9*/("""}"""),format.raw/*99.10*/(""",
      """),format.raw/*100.7*/("""}"""),format.raw/*100.8*/(""";

      var network = new vis.Network(container, data, options);
      
      network.on('select', function(properties) """),format.raw/*104.49*/("""{"""),format.raw/*104.50*/("""
        var select_node = $.grep(data.nodes, function(e)"""),format.raw/*105.57*/("""{"""),format.raw/*105.58*/("""
          return e["id"] == properties.nodes[0];
        """),format.raw/*107.9*/("""}"""),format.raw/*107.10*/(""")[0];
        $("#testText").html(select_node["title"]);
      """),format.raw/*109.7*/("""}"""),format.raw/*109.8*/(""");

      //focus on serviceid 19
      //network.focusOnNode(19);
    
  </script>
  
  
    
""")))})))}
    }
    
    def render(jsonData:String): play.api.templates.HtmlFormat.Appendable = apply(jsonData)
    
    def f:((String) => play.api.templates.HtmlFormat.Appendable) = (jsonData) => apply(jsonData)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Fri Sep 18 18:40:51 PDT 2015
                    SOURCE: /home/xingwei/SOC-Fall-2015/ApacheCMDA-Frontend/app/views/climate/dataRecommend.scala.html
                    HASH: 197d78ab87503a7298833381075df6054703a99b
                    MATRIX: 3185->1205|3297->1518|3312->1525|3397->1529|3452->1548|3467->1554|3529->1594|3595->1624|3610->1630|3671->1669|3760->1722|3775->1728|3842->1773|3895->1223|3923->1241|3994->1276|4009->1282|4077->1328|4248->1463|4263->1469|4325->1509|4360->1786|4398->1789|4436->1818|4475->1819|4521->1830|4558->1845|4644->1895|4674->1903|5185->2386|5214->2387|5283->2428|5312->2429|5358->2447|5387->2448|5484->2517|5513->2518|5550->2528|5579->2529|5624->2546|5653->2547|5763->2630|5792->2631|5838->2649|5867->2650|5912->2667|5941->2668|6035->2734|6064->2735|6113->2756|6142->2757|6240->2827|6269->2828|6318->2849|6347->2850|6440->2915|6469->2916|6515->2934|6544->2935|6640->3003|6669->3004|6705->3013|6734->3014|6781->3033|6810->3034|6963->3159|6992->3160|7093->3233|7122->3234|7158->3243|7187->3244|7223->3252|7252->3253|7402->3374|7432->3375|7518->3432|7548->3433|7634->3491|7664->3492|7755->3555|7784->3556
                    LINES: 56->18|59->31|59->31|61->31|62->32|62->32|62->32|63->33|63->33|63->33|64->34|64->34|64->34|66->18|67->20|69->22|69->22|69->22|76->29|76->29|76->29|78->35|80->37|80->37|80->37|82->39|82->39|84->41|84->41|102->59|102->59|104->61|104->61|105->62|105->62|108->65|108->65|109->66|109->66|110->67|110->67|114->71|114->71|115->72|115->72|116->73|116->73|119->76|119->76|120->77|120->77|123->80|123->80|124->81|124->81|127->84|127->84|128->85|128->85|131->88|131->88|132->89|132->89|133->90|133->90|138->95|138->95|141->98|141->98|142->99|142->99|143->100|143->100|147->104|147->104|148->105|148->105|150->107|150->107|152->109|152->109
                    -- GENERATED --
                */
            