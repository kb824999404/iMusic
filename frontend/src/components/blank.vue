<template>
  <div></div>
</template>

<script>

export default {
  name: 'Blank',
  beforeRouteEnter(to, from, next) {
    next((xq) => {
    //要注意，必须使用this.$router.replace而非this.$router.push
    //如果使用的是this.$router.push会导致，进入过空白页之后，通过浏览器的后退键，无法实现页面后退的bug现象
      xq.$router.replace({name:from.name,params: xq.$route.params});
    });
  }
}
</script>
