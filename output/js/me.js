

function initPieChart(echarts,theme,pipData){
        var myChart = echarts.init(document.getElementById('main1'),theme);
        var option = convertToPipeData(pipData);
        myChart.setOption(option);

}

function initLineChart(echarts,theme,data,id){

                    

       var myChart = echarts.init(document.getElementById(id),theme);
       var option = convertToLineData(data);
       console.log(option);
        myChart.setOption(option);
}

function convertToLineData(data){
    option = {
        title : {
            text: '未来一周气温变化',
            //subtext: '纯属虚构'
        },
        tooltip : {
            trigger: 'axis'
        },
        legend: {
            data:['数量']
        },
        toolbox: {
            show : false,
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar']},
                restore : {show: true},
                saveAsImage : {show: true}
            }
        },
        calculable : true,
        xAxis : [
            {
                type : 'category',
                boundaryGap : false,
                data : ['周一','周二','周三','周四','周五','周六','周日']
            }
        ],
        yAxis : [
            {
                type : 'value',
                axisLabel : {
                    formatter: '{value} '//°C
                }
            }
        ],
        series : [
            {
                name:'数量',
                type:'line',
                data:[110, 101, 150, 130, 120, 130, 100]
            }
            
        ]
    };
    var result = $.extend({},option);
    xAxisArray = [];
    seriesArray= [];
    for(var i=0;i < data.pairs.length;i++){
        xAxisArray.push(data.pairs[i].key);
        var obj = {};
        seriesArray.push(data.pairs[i].value);
    }
    result.title.text = data.title;
    result.series[0].data = seriesArray;
    result.xAxis[0].data = xAxisArray;
    return result;
}


function convertToPipeData(data){
    var option = {
        title : {
            text: '某站点用户访问来源',
            //subtext: '纯属虚构',
            x:'center'
        },
        tooltip : {
            trigger: 'item',
            formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
            orient: 'vertical',
            left: 'left',
            data: ['直接访问','邮件营销','联盟广告','视频广告','搜索引擎']
        },
        series : 
            [
                {
                    name: '访问来源',
                    type: 'pie',
                    radius : '55%',
                    center: ['50%', '60%'],
                    data:[
                        {value:335, name:'直接访问'},
                        {value:310, name:'邮件营销'},
                        {value:234, name:'联盟广告'},
                        {value:135, name:'视频广告'},
                        {value:1548, name:'搜索引擎'}
                    ],
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ],
    };

    var result = $.extend({},option);
    var legendArray = [];
    var seriesArray = [];
    for(var i=0;i < data.pairs.length;i++){
        legendArray.push(data.pairs[i].key);
        var obj = {};
        obj.value = data.pairs[i].value;
        obj.name = data.pairs[i].key;
        seriesArray.push(obj);
    }
    result.title.text = data.title;
    result.series[0].data = seriesArray;
    result.legend.data = legendArray;
    return result;
}


require(['require','test','echarts.min','macarons'],function(require,test,echarts,macarons){
    console.log(macarons);
    initPieChart(echarts,macarons,pipData);
    initLineChart(echarts,macarons,yearData,'main2');
    initLineChart(echarts,macarons,motionData,'main3');

})