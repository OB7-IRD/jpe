/* 
 * Copyright (C) 2015 IRD
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
function getGlobalMenu() {
    $.ajax({
        url: "/getjson",
        type: "post",
        dataType: "json",
        data: {
            jsonPath: "http://mdst-macroes.ird.fr/cdn/MenuEcoscope.json"
        },
        beforeSend: function (xhr) {

        },
        success: function (data)
        {

            var dataset = data;
            var jObjMenu = $("#globalMenu"),
                    jObjModel = $("<li/>", {
                        "html": '<a target="_blank" class="maClasse" href="#">Menu</a>'
                    }),
                    jObjLi = null,
                    jObjA = null,
                    jObjSLi = null,
                    jObjSA = null;
            $.each(dataset[ "menu" ], function (i, item) {
                jObjLi = jObjModel.clone();
                jObjA = jObjLi.find("a");
                jObjA.text(item[ "name" ]);
                jObjA.attr("class", item[ "classe" ]);
                jObjA.attr("href", item[ "url" ]);
                jObjA.attr("title", item[ "info" ]);
                if (item[ "sousMenu" ]) {
                    jObjA.addClass("dropdown-toggle");
                    jObjA.attr("data-toggle",
                            "dropdown-submenu");
                    jObjLi.addClass("dropdown");
                    jObjLi.addClass("dropdown-submenu");
                    jObjLi.append('<ul class="dropdown-menu" > </ul>');

                    $.each(item[ "sousMenu" ], function
                            (j, jtem) {
                        jObjSLi = jObjModel.clone(),
                                jObjSA =
                                jObjSLi.find("a");
                        jObjSA.text(jtem[ "name" ]);
                        jObjSA.attr("class", jtem[
                                "classe" ]);
                        jObjSA.attr("href", jtem[ "url"
                        ]);
                        jObjLi.find("ul").append(jObjSLi);
                        jObjLi.find("ul").css("margin-left", "25%");
                    });
                }

                jObjMenu.append(jObjLi);
            });
        },
        error: function (error)
        {
        }
    });
}
;

(function (jQuery) {
    jQuery.fn.exists = function () {
        return this.length > 0;
    };


});
//$(function () {

//    /* set variables locally for increased performance */
//    var scroll_timer;
//    var displayed = false;
//    var $message = $('#message a');
//    var $window = $(window);
//    var top = $(document.body).children(0).position().top;
//
//    /* react to scroll event on window */
//    $window.scroll(function () {
//        window.clearTimeout(scroll_timer);
//        scroll_timer = window.setTimeout(function () {
//            if ($window.scrollTop() <= top )
//            {
//                displayed = false;
//                $message.fadeOut(500);
//            }
//            else if (displayed === false)
//            {
//                displayed = true;
//                $message.stop(true, true).show().click(function () {
//                    $message.fadeOut(500);
//                });
//            }
//        }, 100);
//    });

//});
