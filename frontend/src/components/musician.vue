<template>
  <el-container class="mainContainer">
    <!-- 导航栏 -->
    <Navbar activeIndex="3"/>
    <!-- 主体 -->
    <el-main>
        <!-- 回到顶部按钮 -->
        <el-backtop>
      </el-backtop>
              <div class="Page">
          <!-- 选择栏 -->
          <div class="selectBar">
            <h1>筛选音乐人</h1>
            <!-- 选择器 -->
            <div class="selecters">
                <el-select v-model="selectCountry" placeholder="国家">
                  <el-option
                    v-for="item in Country"
                    :key="item.countryId"
                    :label="item.countryName"
                    :value="item.countryId">
                  </el-option>
                </el-select>
                <el-select v-model="selectStyle" placeholder="喜欢的风格">
                  <el-option
                    v-for="item in Style"
                    :key="item.styleId"
                    :label="item.styleName"
                    :value="item.styleId">
                  </el-option>
                </el-select>
                <el-select v-model="selectInstrument" placeholder="擅长的乐器">
                  <el-option
                    v-for="item in Instrument"
                    :key="item.instrumentId"
                    :label="item.instrumentName"
                    :value="item.instrumentId">
                  </el-option>
                </el-select>
                <el-button type="primary" @click="regetMusicianData">筛选</el-button>
            </div>
            <!-- 标签 -->
            <div class="tags">
              <el-tag v-if="selectCountry!=''" type="info">
                  {{countryMapper[selectCountry]}}
              </el-tag>
              <el-tag v-if="selectStyle!=''">
                  {{styleMapper[selectStyle]}}
              </el-tag>
              <el-tag v-if="selectInstrument!=''" type="success">
                  {{instrumentMapper[selectInstrument]}}
              </el-tag>
            </div>
          </div>
      <p class="searchResult">找到 {{Musician.length}} 位音乐人</p>
      <el-table
      class="musicTable"
      highlight-current-row
      :data="Musician"
       strip>
      <el-table-column
      type="index"
      width="100">
    </el-table-column>
      <el-table-column
        prop="musicianName"
        label="艺名"
        width="200">
        <template slot-scope="scope">
          <el-button type="text" @click="toMusicianInfo(scope.row)">{{ scope.row.musicianName}}</el-button>
        </template>
      </el-table-column>
      <el-table-column
        prop="description"
        label="描述">
      </el-table-column>
      <el-table-column
        label="国家"
        width="200">
        <template slot-scope="scope">
          <span>{{ countryMapper[scope.row.countryId]}}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="喜欢的风格"
        width="200">
        <template slot-scope="scope">
          <span>{{ styleMapper[scope.row.favMusicStyle]}}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="擅长的乐器"
        width="200">
        <template slot-scope="scope">
          <span>{{ instrumentMapper[scope.row.favInstrument]}}</span>
        </template>
      </el-table-column>
    </el-table>
        </div>

    </el-main>
    <el-footer class="footer">
      <p>&copy; 2020 - iMusic</p>
    </el-footer>
  </el-container>
</template>

<script>
import Navbar from '@/components/navbar'


export default {
  name: 'Musician',
  components:{
    Navbar
  },
  data () {
    return {
    Musician:[],
    Style:[],
    Instrument:[],
    Country:[],
    selectStyle:"",
    selectInstrument:"",
    selectCountry:"",

    styleMapper:{},
    instrumentMapper:{},
    countryMapper:{},
    getDataStatus:[false,false,false]

    }
  },
    created()
  {
    this.getAllStyle();
    this.getAllInstrument();
    this.getAllCountry();

  },
  methods:
  {
    toMusicianInfo(item){
      this.$router.push({path:"musicianInfo?musicianId="+item.musicianId});
    },
    checkStatus(){
      if(this.getDataStatus[0]&&this.getDataStatus[1]&&this.getDataStatus[2])
        this.getAllMusician();
    },
    regetMusicianData(){
      this.Musician=[];
      this.getAllMusician();
    },
    getAllMusician(){
        var form = new FormData();
        var styleId=(this.selectStyle==="")?-1:this.selectStyle;
        var instrumentId=(this.selectInstrument==="")?-1:this.selectInstrument;
        var countryId=(this.selectCountry==="")?-1:this.selectCountry;


        form.append("countryId", countryId);
        form.append("styleId", styleId);
        form.append("instrumentId", instrumentId);
        var settings = {
          "url": this.$backEndUrl+"musician/getAllMusician",
          "method": "POST",
          "timeout": 0,
          "processData": false,
          "mimeType": "multipart/form-data",
          "contentType": false,
          "data": form
        };

        this.$jquery.ajax(settings).done((response)=>  {
                var data=JSON.parse(response);
                console.log(data);
                if (data.status == 'true') {
                    console.log('success');
                    this.Musician=data.musicians;
                }
                else
                {
                    console.log('fail');
                    var message = data.message;
                    alert(message);
                } 
        });
    },
    getAllStyle(){
      var form = new FormData();
      var settings = {
        "url": this.$backEndUrl+"music/getAllStyle",
        "method": "POST",
        "timeout": 0,
        "processData": false,
        "mimeType": "multipart/form-data",
        "contentType": false,
        "data": form
      };

      this.$jquery.ajax(settings).done( (response)=> {
            var data=JSON.parse(response);
            if (data.status == 'true') {
                console.log('success');
                this.Style=data.styles;
                this.Style.forEach((item)=>{
                  this.styleMapper[item.styleId]=item.styleName;
                });
                this.getDataStatus[0]=true;
                this.checkStatus();
            }
            else
            {
                console.log('fail');
                var message = data.message;
                alert(message);
            }   
      });
    },
    getAllInstrument(){
      var form = new FormData();
      var settings = {
        "url": this.$backEndUrl+"music/getAllInstrument",
        "method": "POST",
        "timeout": 0,
        "processData": false,
        "mimeType": "multipart/form-data",
        "contentType": false,
        "data": form
      };

      this.$jquery.ajax(settings).done( (response)=> {
            var data=JSON.parse(response);
            if (data.status == 'true') {
                console.log('success');
                this.Instrument=data.instruments;
                this.Instrument.forEach((item)=>{
                  this.instrumentMapper[item.instrumentId]=item.instrumentName;
                });
                this.getDataStatus[1]=true;
                this.checkStatus();

            }
            else
            {
                console.log('fail');
                var message = data.message;
                alert(message);
            }   
      });
    },
    getAllCountry(){
      var form = new FormData();
      var settings = {
        "url": this.$backEndUrl+"musician/getAllCountry",
        "method": "POST",
        "timeout": 0,
        "processData": false,
        "mimeType": "multipart/form-data",
        "contentType": false,
        "data": form
      };

      this.$jquery.ajax(settings).done( (response)=> {
            var data=JSON.parse(response);
            if (data.status == 'true') {
                console.log('success');
                this.Country=data.countrys;
                this.Country.forEach((item)=>{
                  this.countryMapper[item.countryId]=item.countryName;
                });
                this.getDataStatus[2]=true;
                this.checkStatus();

            }
            else
            {
                console.log('fail');
                var message = data.message;
                alert(message);
            }   
      });
    },
  }
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped src="../styles/footer.css"></style>
<style scoped src="../styles/musician.css"></style>
