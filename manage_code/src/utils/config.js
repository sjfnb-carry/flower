const config = {
    get() {
        return {
            url : process.env.VUE_APP_BASE_API_URL + process.env.VUE_APP_BASE_API + '/',
            name: process.env.VUE_APP_BASE_API,
        }
    },
    getProjectName(){
        return {
            projectName: `基于微信小程序的海外拼团购物平台的设计和实现`
        } 
    }
}
export default config
