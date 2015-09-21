
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
object recommend extends BaseScalaTemplate[play.api.templates.HtmlFormat.Appendable,Format[play.api.templates.HtmlFormat.Appendable]](play.api.templates.HtmlFormat) with play.api.templates.Template1[String,play.api.templates.HtmlFormat.Appendable] {

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
	<script src='"""),_display_(Seq[Any](/*32.16*/routes/*32.22*/.Assets.at("javascripts/edit_button.js"))),format.raw/*32.62*/("""'></script>
	<script src='"""),_display_(Seq[Any](/*33.16*/routes/*33.22*/.Assets.at("javascripts/livefilter.js"))),format.raw/*33.61*/("""'></script>
	<script type="text/javascript" src='"""),_display_(Seq[Any](/*34.39*/routes/*34.45*/.Assets.at("javascripts/jquery-1.9.0.min.js"))),format.raw/*34.90*/("""'></script>
""")))};
Seq[Any](format.raw/*18.20*/("""
"""),format.raw/*20.1*/("""
<link rel="stylesheet"
	href='"""),_display_(Seq[Any](/*22.9*/routes/*22.15*/.Assets.at("stylesheets/custom_recommend.css"))),format.raw/*22.61*/("""'>

<link rel="stylesheet"
  href="https://cdnjs.cloudflare.com/ajax/libs/vis/3.11.0/vis.min.css"
>

<link rel="stylesheet"
	href='"""),_display_(Seq[Any](/*29.9*/routes/*29.15*/.Assets.at("stylesheets/livefitler.css"))),format.raw/*29.55*/("""'>
	
"""),format.raw/*35.2*/("""

"""),_display_(Seq[Any](/*37.2*/main("Service Logs", scripts)/*37.31*/{_display_(Seq[Any](format.raw/*37.32*/("""
	
	"""),_display_(Seq[Any](/*39.3*/flash_message())),format.raw/*39.18*/("""   

  <div id="jsonData" style="display: none;">"""),_display_(Seq[Any](/*41.46*/jsonData)),format.raw/*41.54*/("""</div>
  <div id="graphText">
    <div class="row">
      <div class="col-lg-6" id="testGraph">
      </div>
      <div class="col-lg-6" id="testText">
      </div>
    </div>
  </div>


  <script src="https://cdnjs.cloudflare.com/ajax/libs/vis/3.11.0/vis.min.js"></script>
  <script type="text/javascript">
  	  var jsonString = $('#jsonData').text();
  	 	var data = JSON.parse(jsonString);
      var container = document.getElementById('testGraph');
      var options = """),format.raw/*57.21*/("""{"""),format.raw/*57.22*/("""
        stabilize:false,
        edges: """),format.raw/*59.16*/("""{"""),format.raw/*59.17*/("""
          color: """),format.raw/*60.18*/("""{"""),format.raw/*60.19*/("""
            color: "gray",
            highlight: "gray",
          """),format.raw/*63.11*/("""}"""),format.raw/*63.12*/(""",
        """),format.raw/*64.9*/("""}"""),format.raw/*64.10*/(""",
        nodes: """),format.raw/*65.16*/("""{"""),format.raw/*65.17*/("""
          shape: 'dot',
          radiusMin: 10,
          radiusMax: 30,
        """),format.raw/*69.9*/("""}"""),format.raw/*69.10*/(""",
        groups: """),format.raw/*70.17*/("""{"""),format.raw/*70.18*/("""
          user: """),format.raw/*71.17*/("""{"""),format.raw/*71.18*/("""
            color:"#F2545A",
            shape:"star",
          """),format.raw/*74.11*/("""}"""),format.raw/*74.12*/(""",
          feature: """),format.raw/*75.20*/("""{"""),format.raw/*75.21*/("""
            shape:"triangle",
            color:"#EC8F93",
          """),format.raw/*78.11*/("""}"""),format.raw/*78.12*/(""",
          service: """),format.raw/*79.20*/("""{"""),format.raw/*79.21*/("""
            color:"#EF777C",
            shape:"dot",
          """),format.raw/*82.11*/("""}"""),format.raw/*82.12*/(""",
          /*data: """),format.raw/*83.19*/("""{"""),format.raw/*83.20*/("""
            color:"#F46369",
            shape:"square",
          """),format.raw/*86.11*/("""}"""),format.raw/*86.12*/("""*/
        """),format.raw/*87.9*/("""}"""),format.raw/*87.10*/(""",
        tooltip: """),format.raw/*88.18*/("""{"""),format.raw/*88.19*/("""
          delay: 300,
          fontColor: "black",
          fontSize: 14, // px
          fontFace: "verdana",
          color: """),format.raw/*93.18*/("""{"""),format.raw/*93.19*/("""
            border: "#666",
            background: "#FFFFC6"
          """),format.raw/*96.11*/("""}"""),format.raw/*96.12*/("""
        """),format.raw/*97.9*/("""}"""),format.raw/*97.10*/("""
      """),format.raw/*98.7*/("""}"""),format.raw/*98.8*/(""";

      var network = new vis.Network(container, data, options);
      //focus on serviceid 19
      network.focusOnNode(19);
      network.on('select', function(properties) """),format.raw/*103.49*/("""{"""),format.raw/*103.50*/("""
        var select_node = $.grep(data.nodes, function(e)"""),format.raw/*104.57*/("""{"""),format.raw/*104.58*/("""
          return e["id"] == properties.nodes[0];
        """),format.raw/*106.9*/("""}"""),format.raw/*106.10*/(""")[0];
        if(select_node["group"] == "service")"""),format.raw/*107.46*/("""{"""),format.raw/*107.47*/("""
          var select_edges = $.grep(data.edges, function(e) """),format.raw/*108.61*/("""{"""),format.raw/*108.62*/(""" 
            return e["from"] == select_node["id"] """),format.raw/*109.51*/("""}"""),format.raw/*109.52*/(""");
          var textVal = "";
          textVal += "<h3>"+select_node["label"]+"</h3>";
          textVal += "<div><h4>Keywords:</h4><h4>"
          for (var i = select_edges.length - 1; i >= 0; i--) """),format.raw/*113.62*/("""{"""),format.raw/*113.63*/("""
            textVal += "<span class=\"label label-primary\">"+select_edges[i]["to"]+"</span>\n";
          """),format.raw/*115.11*/("""}"""),format.raw/*115.12*/(""";
          textVal += "</h4></div>";
          textVal += "<div><img src=\""+select_node["image"]+"\" class=\"img-responsive\"></div>";
          $("#testText").html(textVal);
        """),format.raw/*119.9*/("""}"""),format.raw/*119.10*/("""
      """),format.raw/*120.7*/("""}"""),format.raw/*120.8*/(""");
  </script>
  
  
	
""")))})),format.raw/*125.2*/("""
"""))}
    }
    
    def render(jsonData:String): play.api.templates.HtmlFormat.Appendable = apply(jsonData)
    
    def f:((String) => play.api.templates.HtmlFormat.Appendable) = (jsonData) => apply(jsonData)
    
    def ref: this.type = this

}
                /*
                    -- GENERATED --
                    DATE: Fri Sep 18 18:40:51 PDT 2015
                    SOURCE: /home/xingwei/SOC-Fall-2015/ApacheCMDA-Frontend/app/views/climate/recommend.scala.html
                    HASH: fa1ff65d0caa5f9269dcebbdc94577390e06b458
                    MATRIX: 3181->1205|3293->1509|3308->1516|3393->1520|3445->1536|3460->1542|3522->1582|3585->1609|3600->1615|3661->1654|3747->1704|3762->1710|3829->1755|3882->1223|3910->1241|3977->1273|3992->1279|4060->1325|4227->1457|4242->1463|4304->1503|4336->1768|4374->1771|4412->1800|4451->1801|4491->1806|4528->1821|4614->1871|4644->1879|5145->2352|5174->2353|5243->2394|5272->2395|5318->2413|5347->2414|5444->2483|5473->2484|5510->2494|5539->2495|5584->2512|5613->2513|5723->2596|5752->2597|5798->2615|5827->2616|5872->2633|5901->2634|5995->2700|6024->2701|6073->2722|6102->2723|6200->2793|6229->2794|6278->2815|6307->2816|6400->2881|6429->2882|6477->2902|6506->2903|6602->2971|6631->2972|6669->2983|6698->2984|6745->3003|6774->3004|6933->3135|6962->3136|7063->3209|7092->3210|7128->3219|7157->3220|7191->3227|7219->3228|7423->3403|7453->3404|7539->3461|7569->3462|7655->3520|7685->3521|7765->3572|7795->3573|7885->3634|7915->3635|7996->3687|8026->3688|8256->3889|8286->3890|8423->3998|8453->3999|8666->4184|8696->4185|8731->4192|8760->4193|8816->4217
                    LINES: 56->18|59->31|59->31|61->31|62->32|62->32|62->32|63->33|63->33|63->33|64->34|64->34|64->34|66->18|67->20|69->22|69->22|69->22|76->29|76->29|76->29|78->35|80->37|80->37|80->37|82->39|82->39|84->41|84->41|100->57|100->57|102->59|102->59|103->60|103->60|106->63|106->63|107->64|107->64|108->65|108->65|112->69|112->69|113->70|113->70|114->71|114->71|117->74|117->74|118->75|118->75|121->78|121->78|122->79|122->79|125->82|125->82|126->83|126->83|129->86|129->86|130->87|130->87|131->88|131->88|136->93|136->93|139->96|139->96|140->97|140->97|141->98|141->98|146->103|146->103|147->104|147->104|149->106|149->106|150->107|150->107|151->108|151->108|152->109|152->109|156->113|156->113|158->115|158->115|162->119|162->119|163->120|163->120|168->125
                    -- GENERATED --
                */
            