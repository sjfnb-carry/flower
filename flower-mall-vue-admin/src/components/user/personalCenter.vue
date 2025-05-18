<template>

    <div style="height: 100%;">
        <div class="bode_main main-ka">
            <div class="center_zx">
                <div class="msmsm">
                    <div class="ksk">
                        <img width="100" height="100"
                             src="https://p26-tt.byteimg.com/large/pgc-image/64ea9f995b954df88fad1fb138338831">
                        <p style='color: #EEEEEE'>{{userInfo.username}}</p>
                    </div>
                    <el-row style="margin: 20px">
                        <el-button type="warning" size='mini'>修改密码</el-button>
                        <el-button type="danger" @click.native="loginOut" size='mini'>退出登陆</el-button>
                    </el-row>
                </div>
            </div>
            <div class="center_right">
                <el-tabs>
                    <el-tab-pane label="基本信息">
                        <el-form :inline="true" :model="userInfo" class="demo-form-inline">
                            <el-col :span="11">
                                <el-form-item label="用户名称">
                                    <el-input size='mini' v-model="userInfo.username" placeholder="审批人" disabled></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="11">
                                <el-form-item label="电话号码">
                                    <el-input size='mini' v-model="userInfo.mobile" placeholder="审批人" disabled></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="11">
                                <el-form-item label="邮箱账号">
                                    <el-input size='mini' v-model="userInfo.email" placeholder="审批人" disabled></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="11">
                                <el-form-item label="角色名称">
                                    <el-input size='mini' v-model="userInfo.roleName" placeholder="审批人" disabled></el-input>
                                </el-form-item>
                            </el-col>

                        </el-form>
                    </el-tab-pane>
                </el-tabs>
            </div>
        </div>
<!--        <div class="x_main">-->
<!--            <div class="z_ka main-ka">-->
<!--                &lt;!&ndash;                <div style="margin: auto;width: 100%; height: 100%;" autoplay="autoplay" number="0.5">&ndash;&gt;-->
<!--                &lt;!&ndash;                    <video controls="controls" srcposter="https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1608135368588&di=d345da667a910c4a017bb7fa290d2772&imgtype=0&src=http%3A%2F%2Fp3.music.126.net%2Fl0cLCU25FSydVgnK_lWogg%3D%3D%2F109951163579582364.jpg" autoplay="autoplay">&ndash;&gt;-->
<!--                &lt;!&ndash;                        <source src="../../../public/1.1(Av20668494,P1).mp4" type="video/mp4" >&ndash;&gt;-->
<!--                &lt;!&ndash;                    </video>&ndash;&gt;-->
<!--                &lt;!&ndash;                </div>&ndash;&gt;-->

<!--            </div>-->
<!--            <div class="y_ka main-ka">-->
<!--                <div class="tableDataDey1">-->

<!--                </div>-->

<!--            </div>-->
<!--        </div>-->

    </div>

</template>
<style lang="less" scoped>
    #myChart4 {
        height: 80%;
        width: 100%;
    }

    video {
        width: 100%;
        height: 100%;
        margin: auto;
    }

    .msmsm {
        width: 70%;
        height: 100%;

        .ksk {
            height: 65%;
 
        }

        img {
            margin-top: 10%;
        }
    }

    .bode_main {
        height: 40%;
        width: 80%;
        margin-left: 10%;
        display: flex;
        align-items: center;

    }

    .x_main {
        height: 55%;
        width: 80%;
        margin-left: 10%;
        margin-top: 20px;
        display: flex;
        align-items: stretch;
    }

    .z_ka {
        height: 100%;
        width: 49%;
        display: flex;
        align-items: center;


    }

    .y_ka {
        height: 100%;
        width: 49%;
        margin-left: 2%;
        /*padding: 10px;*/
    }

    .main-ka {
        box-shadow: 0 1px 1px rgba(141, 221, 121, 0.15) !important;
        border: 1px solid #ecebed;
        color: #303133;
        transition: .3s;

    }

    /*.stuff {*/
    /*    background: #fff;*/
    /*    border: 1px solid #dadce0;*/
    /*    border-radius: 8px;*/
    /*    height: 60%;*/
    /*    width: 25%;*/
    /*    margin: 8px;*/
    /*}*/
    .center_zx {
        width: 30%;
        height: 90%;
        border-right: 1px solid #1D1C2E;
        margin: 5px;
        display: flex;
        justify-content: center;
        text-align: center;
    }

    .center_right {
        width: 65%;
        height: 90%;
        margin-left: 2%;

    }

    .el-tabs--border-card > .el-tabs__content {
        padding: 15px;
        height: 100%;
    }

    .el-tabs {
        height: 100%;
    }

    .tableDataDey1 {
        width: 90%;
        height: 80%;
        padding-top: 10px;
        margin: auto;
    }
</style>

<script>
  export default {
    data() {
      return {
        userInfo:{},


      }
    },
    mounted() {
      this.queryList();

    },
    methods: {
      async queryList() {
        const { data: res } = await this.$http.get(
          '/mallSysUser/getByIdThis'
        )

        // console.log(res.data.countData)
        if (res.code != 20000) return this.$message.error(res.message)
        this.loginIp = res.data.loginIp
        this.operatingLog = res.data.operatingLog
        this.archivesData = res.data.archivesData
        this.userInfo = res.data.userInfoAll

      },
      loginOut() {
        window.sessionStorage.clear()
        window.location.reload
        this.$router.push('/login')
      }
    }
  }
</script>
