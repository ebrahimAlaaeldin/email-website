import { createStore } from 'vuex';

export default createStore({
  state() {
    return {
      hashMap: {},
    };
  },
  mutations: {
    setHashMap(state, payload) {
      state.hashMap = payload;
    },
  },
  actions: {
    updateHashMap({ commit }, newHashMap) {
      commit('setHashMap', newHashMap);
    },
  },
  getters: {
    getHashMap: (state) => state.hashMap,
  },
});
