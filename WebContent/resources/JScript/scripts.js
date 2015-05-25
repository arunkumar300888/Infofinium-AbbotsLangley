//We don't use any other programing language to include those files. just jQuery
//Define the folder where the content of each window/widget is located
//We call widget the box that have a black border and you can resize/move it
var testhtml="<li class='grid-box view-action-trays' data-rowId='view-action-trays' data-row='1' data-col='1' data-sizex='4' data-sizey='1'>"
+"<div class='inner-box'  id='header2'>"
+"<span class='grid-box-header'>ACTION TRAYS"
+"<a href='#' class='close-widget-box'>"
+"<img src='resources/images/wizart/x.png' class='float-right' />"
+"</a></span>"			
+"<div class='list-box'>"
+"<ul>"
+"<li class='one button-action-trays'>"
+"<a href='#' class='button1 float-left'>"
+"<div class='float-left button-zeros'>"
+"0000"
+"</div>"
+"<div class='button-text'>"
+"DOCUMENTS"
+"</div>"
+"</a>	"		
+"</li>	"
+"</ul>"
+"</div>"
+"<div class='clear'></div>"
+"</div>"
+"</li>";

var templateMapPath = 'views/';
//In this array we define the widgets
//Stucture: 'id':'file_name'
function refreshTray() {
	// console.log("refreshtray");
	$.ajax({
		type : "GET",
		url : "getTray",
		cache : false,
		success : function(data) {
			 alert(data);
		}
	});
}
var templateMap={
  'view-action-trays' : testhtml,
  'view-last-downloads': testhtml,
  'view-last-actioned': testhtml,
  'view-my-documents': testhtml,
  'view-downloaded': testhtml,
  'view-upload': testhtml,
  'view-search': testhtml,
  'view-new-form' : testhtml
};
/*
$.each(templateMap, function(key, data) {
    alert(data);
});*/
//This is the default order of the widgets
//Feel free to modify these numbers as you want to see how it works
var defaultBoxLayout = {
  'view-action-trays' : {
    'data-row' : 1,
    'data-col' : 1,
    'data-sizex': 4,
    'data-sizey': 1
  },
  'view-last-downloads' : {
    'data-row' : 2,
    'data-col' : 1,
    'data-sizex': 2,
    'data-sizey': 2
  },     
  'view-last-actioned' : {
    'data-row' : 2,
    'data-col' : 3,
    'data-sizex': 2,
    'data-sizey': 2
  },     
  'view-my-documents' : {
    'data-row' : 4,
    'data-col' : 1,
    'data-sizex': 4,
    'data-sizey': 4
  }
};

//enable widgets. if you declare a widget in first array but you don't include it in this one the widget will not be imported 
var defaultOnWidgets = ['view-action-trays','view-last-downloads','view-last-actioned','view-my-documents'];

function sortObjects(a,b){
  a=$('.'+a);
  b=$('.'+b);
  if(a.attr('data-row') > b.attr('data-row') ){
    return 1;
  }else if(a.attr('data-row') < b.attr('data-row') ){
    return -1;
  }else{
    if(a.attr('data-col') > a.attr('data-col') ){
      return 1;
    }else if(a.attr('data-col') < b.attr('data-col') ){
      return -1;
    }else{
      return 0;
    }
  }
}



function getActiveBoxes(){
  var temp = localStorage.getItem('activeWidgets');
  return temp? temp.split(',') : null;
}

function saveActiveBoxes(){

  var arr = [];
  $('input[name=visibleWidgets]:checked').each(function(){
    if(! arr.indexOf($(this).val()) > -1)
      arr.push($(this).val());
  });
  console.log('before sort',arr);
  arr.sort(sortObjects);
    console.log('saveing active',arr);
  localStorage.setItem('activeWidgets',arr.join(','));
}


function getBoxLayout(){
  //TODO implement propper get box layou from user prefferences saved on server
   return JSON.parse(localStorage.getItem('BoxLayout'));
}

function saveBoxLayout(){
  //TODO implement propper save box layou to server
    jQuery(function($){
        var newLayout= {};
        activewidget=[];
        $('.gridster .grid-box').each(function(){
           activewidget.push(this);
           
        });

        $.each(activewidget,function(){
           var $this=$(this);
            newLayout[$this.attr('data-rowId')] = {
                'data-row' : $this.attr('data-row'),
                'data-col' : $this.attr('data-col'),
                'data-sizex': $this.attr('data-sizex'),
                'data-sizey': $this.attr('data-sizey')
            };
        });
        localStorage.setItem('layoutLock',$gridster.drag_api.disabled);
        localStorage.setItem('BoxLayout',JSON.stringify(newLayout) );  
    });
}

function resetWidgets(){
  //TODO remove user prefferences from server
  localStorage.removeItem('activeWidgets');
  localStorage.removeItem('BoxLayout');
  document.location.reload();
}
function toggleLayoutLock(){
  if($gridster.drag_api.disabled){
    $gridster.enable();
    $('#toggleLayoutLock').text("LOCK LAYOUT");
  }else{
    $gridster.disable();
    $('#toggleLayoutLock').text("UNLOCK LAYOUT");

  }
}

var $gridster = null;

jQuery(function($){

  var wrapperWidth= $('#wrapper').width();
  setTimeout(function(){
    $(".place-table").width($("#wrapper").width());
  },1000);

    $(".hover").hover(function(){$("#meniu-admin").addClass("onHover");},function(){$("#meniu-admin").removeClass("onHover");});
    $(".with-hover").hover(function(){$("#view-widget-checkbox-container").addClass("onHover");},function(){$("#view-widget-checkbox-container").removeClass("onHover");});

  var activeWidgets = getActiveBoxes() || defaultOnWidgets;
  var layout = getBoxLayout() || defaultBoxLayout;
  widgetLoadedIndex = 0;
  $gridster =  $("ul.gridster").gridster({
        widget_margins: [7, 7],
        widget_base_dimensions: [(wrapperWidth/4)-14, 75],
        avoid_overlapped_widgets : true,
        min_cols: 4,
        resize : {
           enabled: true,
           stop : function(e, ui, $widget){
            // console.log('reiniti', $widget.find('.box-content'))
            // $widget.find('.box-content').data('jsp').reinitialise();
            // saveBoxLayout();
           }
        },  
        draggable : {
          stop : function(){
            // saveBoxLayout();
          }
        }
    }).data('gridster');
  if(localStorage.getItem('layoutLock')){
    toggleLayoutLock();
  }

  $gridster.LoadWidget = function(widgetId){
	 // alert("Widget ID:" +widgetId);
    if($('.'+widgetId).length){
      console.log('add widget faild,allready exists',widgetId);
       return;
    }
    //$.get(templateMapPath+templateMap[widgetId])
    

    $.each(templateMap, function(key, data) {  
        	//alert(layout[widgetId]+'ss');   
          wl = layout[widgetId] || {
            'data-row' : 1,
            'data-col' : 1,
            'data-sizex': 4,
            'data-sizey': 2
          };
           
          $gridster.add_widget(data,wl['data-sizex'],wl['data-sizey'],wl['data-col'],wl['data-row']);
          // $('input[name=visibleWidgets,value='+widgetId+']').prop('checked',true);
          // saveActiveBoxes();
          widgetLoadedIndex++;
          if(widgetLoadedIndex == activeWidgets.length)
          recalculateGridsterSize();
          if( $('.'+widgetId+' .inner-box .box-content').length){            
            $('.'+widgetId+' .inner-box .box-content').jScrollPane({
              horizontalGutter: 3,
              verticalGutter: 3,
              autoReinitialise: true
            });
          }
        })
        .fail(function(e) {
        	alert('error loading widget');
          console.log( "error loading widget",e );
        });
  };
  $gridster.removeWidget = function(widgetId){
    var el = $('.'+widgetId);
      delete layout[widgetId];
    if(el.length){
       console.log('remove',widgetId);
      this.remove_widget($('.'+widgetId));
      // $('input[name=visibleWidgets,value='+widgetId+']').prop('checked',false);
    }else{
       console.log('remove faild no widget with id',widgetId);
    }
    // saveActiveBoxes();
  };
  
  $('ul.gridster').on('click','.close-widget-box', function(e){
	//  alert(e);
    var widgetId = $(this).parents('.grid-box').attr('data-rowid');
   // alert("widgetId:" +widgetId);
    console.log($('input[name=visibleWidgets][value='+widgetId+']'));
        $('input[name=visibleWidgets][value='+widgetId+']').prop('checked',false);
        $gridster.removeWidget(widgetId);
  });

  for (var i  in activeWidgets) {
    var widgetId = activeWidgets[i];

    $gridster.LoadWidget(widgetId);
    $('input[name=visibleWidgets][value='+widgetId+']').prop('checked',true);
  };

  $(window).resize(function(){
    recalculateGridsterSize();
  });

  $('input[name=visibleWidgets]').change(function(e){
    var widgetId =$(this).val();
    // console.log($(this).prop('checked'),widgetId)
    // return;
    if($(this).prop('checked')){//add widget
      $gridster.LoadWidget(widgetId);
    }else{ //remove widget
      $gridster.removeWidget(widgetId);
    }
    saveActiveBoxes();
  });

  $('#show-search-widget').click(function(){
    if( $('.view-search').length){
        $gridster.removeWidget('view-search');
      $('.view-search').remove();
    }
  
    $gridster.LoadWidget('view-search');
  });

});

function recalculateGridsterSize(){
    $gridster.resize_widget_dimensions({widget_base_dimensions: [($('#wrapper').width()/4)-14,  75]});
}

var myDocumentHandler = {
    loadFileDetails : function(filename){
      filename=filename||"";
      $.get('/documentParts/details.html?filename='+filename)
      .done(function(data){
        myDocumentHandler.replaceMyDocumentsContent(data);
      }) .fail(function(e) {
          console.log( "error loading file",e );
        });
    },
    replaceMyDocumentsContent : function(html){
        if(!$('.view-my-documents').length)
          $gridster.LoadWidget('view-my-documents');
        $('.view-my-documents').html(html);
    },
    GoToHome : function(){
      $.get('/documentParts/all.html').done(function(data){
        myDocumentHandler.replaceMyDocumentsContent(data);
      }) .fail(function(e) {
          console.log( "error loading file",e );
      });
    }


 };
$(document).on('click','#details-go-back',function(){
  console.log('details-go-back');
  myDocumentHandler.GoToHome();
  return false;
});