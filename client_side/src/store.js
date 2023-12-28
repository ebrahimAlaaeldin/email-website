// store.js
import { createStore } from 'vuex';

export default createStore({
  state() {
    return {
      hashMap: {},
      sortCriteria: 'body',
      pageNumber: 1,
    };
  },
  mutations: {
    setHashMap(state, payload) {
      state.hashMap = payload;
    },
    setSortCriteria(state, sortCriteria) {
      state.sortCriteria = sortCriteria;
    },
    setPageNumber(state, pageNumber) {
      state.pageNumber = pageNumber;
    },
  },
  actions: {
    updateHashMap({ commit }, newHashMap) {
      commit('setHashMap', newHashMap);
    },
    updateSortCriteria({ commit }, sortCriteria) {
      commit('setSortCriteria', sortCriteria);
    },
    updatePageNumber({ commit }, pageNumber) {
      commit('setPageNumber', pageNumber);
    },
  },
  getters: {
    getHashMap: (state) => state.hashMap,
    getSortCriteria: (state) => state.sortCriteria,
    getPageNumber: (state) => state.pageNumber,
  },
});
