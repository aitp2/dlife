webpackJsonp([0],[
/* 0 */,
/* 1 */,
/* 2 */,
/* 3 */,
/* 4 */,
/* 5 */,
/* 6 */,
/* 7 */,
/* 8 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _index = __webpack_require__(313);

var API_CONFIG = {
  /**
   * Fitness Activity Resource
   */
  getAllFitnessActivities: {
    url: _index.API_SERVER + '/fitness-activities',
    method: 'get',
    headers: false
  },
  createFitnessActivity: {
    url: _index.API_SERVER + '/fitness-activities',
    method: 'post',
    headers: false
  },
  getFitnessActicity: {
    url: _index.API_SERVER + '/fitness-activities/{{id}}',
    method: 'get',
    headers: false
  },
  deleteFitnessActivity: {
    url: _index.API_SERVER + '/fitness-activities/{{id}}',
    method: 'delete',
    headers: false
  },
  updateFitnessActivity: {
    url: _index.API_SERVER + '/fitness-activities',
    method: 'put',
    headers: false
  },
  getActivitiesByWechatUserId: {
    url: _index.API_SERVER + '/fitness-activities/getActivitiesByWechatUserId',
    method: 'get',
    headers: false
  },
  viewCreateFitnessActivity: {
    url: _index.API_SERVER + '/fitness-activities/createView/{{wechatUserId}}',
    method: 'get',
    headers: false
  },

  /**
   * Fitness Activity Participation Resource
   */
  getAllActivityParticipations: {
    url: _index.API_SERVER + '/activity-participation',
    method: 'get',
    headers: false
  },
  createActivityParticipation: {
    url: _index.API_SERVER + '/activity-participations',
    method: 'post',
    headers: false
  },
  deleteActivityParticipation: {
    url: _index.API_SERVER + '/activity-participations/{{id}}',
    method: 'delete',
    headers: false
  },
  getActivityParticipation: {
    url: _index.API_SERVER + '/activity-participations/{{wechatUserId}}/{{activityId}}',
    method: 'get',
    headers: false
  },
  getParticipationsByActivityId: {
    url: _index.API_SERVER + '/activity-participations/getParticipationsByActivityId',
    method: 'get',
    headers: false
  },

  /**
   * Fitness Pictures Resource
   */
  getAllPics: {
    url: _index.API_SERVER + '/pics',
    method: 'get',
    headers: false
  },
  createPics: {
    url: _index.API_SERVER + '/pics',
    method: 'post',
    headers: false
  },
  createMultiPics: {
    url: _index.API_SERVER + '/multi-pics',
    method: 'post',
    headers: false
  },
  getPics: {
    url: _index.API_SERVER + '/pics/{{id}}',
    method: 'get',
    headers: false
  },

  /**
   * Fitness Clockin Resource
   */
  getAllClockIns: {
    url: _index.API_SERVER + '/clock-ins',
    method: 'get',
    headers: false
  },
  createClockIn: {
    url: _index.API_SERVER + '/clock-ins',
    method: 'post',
    headers: false
  },
  getClockIn: {
    url: _index.API_SERVER + '/clock-ins/{{id}}',
    method: 'get',
    headers: false
  },
  getClockinsByActivityParticipationId: {
    url: _index.API_SERVER + '/clock-ins/getClockinsByActivityParticipationId',
    method: 'get',
    headers: false
  },
  getClockinsByWechatUserIdAndDate: {
    url: _index.API_SERVER + '/clock-ins/getClockinsByWechatUserIdAndDate',
    method: 'get',
    headers: false
  },

  /**
   * Fitness Clockin Summary Resource
   */
  getClockinSummaryByWechatId: {
    url: _index.API_SERVER + '/clockin-summaries/getByWechatUserId',
    method: 'get',
    headers: false
  },

  /**
   * Wechat User Resource
   */
  getAllWechatUsers: {
    url: _index.API_SERVER + '/wechat-users',
    method: 'get',
    headers: false
  },
  updateWechatUser: {
    url: _index.API_SERVER + '/wechat-users',
    method: 'put',
    headers: false
  },
  getWechatUser: {
    url: _index.API_SERVER + '/wechat-users/{{id}}',
    method: 'get',
    headers: false
  }
};

exports.default = API_CONFIG;

/***/ }),
/* 9 */,
/* 10 */,
/* 11 */,
/* 12 */,
/* 13 */,
/* 14 */,
/* 15 */,
/* 16 */,
/* 17 */,
/* 18 */,
/* 19 */,
/* 20 */,
/* 21 */,
/* 22 */,
/* 23 */,
/* 24 */,
/* 25 */,
/* 26 */,
/* 27 */,
/* 28 */,
/* 29 */,
/* 30 */,
/* 31 */,
/* 32 */,
/* 33 */,
/* 34 */,
/* 35 */,
/* 36 */,
/* 37 */,
/* 38 */,
/* 39 */,
/* 40 */,
/* 41 */,
/* 42 */,
/* 43 */,
/* 44 */,
/* 45 */,
/* 46 */,
/* 47 */,
/* 48 */,
/* 49 */,
/* 50 */,
/* 51 */,
/* 52 */,
/* 53 */,
/* 54 */,
/* 55 */,
/* 56 */,
/* 57 */,
/* 58 */,
/* 59 */,
/* 60 */,
/* 61 */,
/* 62 */,
/* 63 */,
/* 64 */,
/* 65 */,
/* 66 */,
/* 67 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _regenerator = __webpack_require__(0);

var _regenerator2 = _interopRequireDefault(_regenerator);

var _assign = __webpack_require__(4);

var _assign2 = _interopRequireDefault(_assign);

var _asyncToGenerator2 = __webpack_require__(1);

var _asyncToGenerator3 = _interopRequireDefault(_asyncToGenerator2);

var _utils = __webpack_require__(2);

var _api = __webpack_require__(8);

var _api2 = _interopRequireDefault(_api);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

/**
 * the response likes below:
 * {
"activityEndTime": "2018-06-19T16:24:33.527Z",
"activityStartTime": "2018-06-19T16:24:33.527Z",
"avatar": "string",
"companyRole": "string",
"descrption": "string",
"id": 0,
"nickName": "string",
"project": "string",
"signEndTime": "2018-06-19T16:24:33.527Z",
"signStartTime": "2018-06-19T16:24:33.527Z",
"title": "string",
"wechatUserId": "string"
}
 *
 *
 */
exports.default = function () {
  var _ref = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee(id) {
    var url, _params, res;

    return _regenerator2.default.wrap(function _callee$(_context) {
      while (1) {
        switch (_context.prev = _context.next) {
          case 0:
            url = _api2.default.getFitnessActicity.url.replace('{{id}}', id);
            _params = (0, _assign2.default)({}, _api2.default.getFitnessActicity, {
              url: url
            });
            _context.next = 4;
            return (0, _utils.fetchAPI)(_params);

          case 4:
            res = _context.sent;


            console.log('getFitnessActicity response: ', res);

            return _context.abrupt('return', res);

          case 7:
          case 'end':
            return _context.stop();
        }
      }
    }, _callee, this);
  }));

  function getFitnessActicity(_x) {
    return _ref.apply(this, arguments);
  }

  return getFitnessActicity;
}();

/***/ }),
/* 68 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _regenerator = __webpack_require__(0);

var _regenerator2 = _interopRequireDefault(_regenerator);

var _assign = __webpack_require__(4);

var _assign2 = _interopRequireDefault(_assign);

var _asyncToGenerator2 = __webpack_require__(1);

var _asyncToGenerator3 = _interopRequireDefault(_asyncToGenerator2);

var _utils = __webpack_require__(2);

var _api = __webpack_require__(8);

var _api2 = _interopRequireDefault(_api);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

/*{
  "activityId": 0,
  "avatar": "string",
  "clockinCount": 0,
  "id": 0,
  "nickName": "string",
  "participationTime": "string",
  "project": "string",
  "wechatUserId": "string"
}*/

exports.default = function () {
  var _ref = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee2() {
    var params = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : {
      activityId: activityId
    };
    return _regenerator2.default.wrap(function _callee2$(_context2) {
      while (1) {
        switch (_context2.prev = _context2.next) {
          case 0:
            return _context2.abrupt('return', (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee() {
              var activityId, _search, url, _params, res;

              return _regenerator2.default.wrap(function _callee$(_context) {
                while (1) {
                  switch (_context.prev = _context.next) {
                    case 0:
                      activityId = params.activityId;

                      if (activityId) {
                        _context.next = 3;
                        break;
                      }

                      return _context.abrupt('return');

                    case 3:
                      _search = (0, _utils.joinParams)(params);
                      url = _api2.default.getParticipationsByActivityId.url + '/?' + _search;
                      _params = (0, _assign2.default)({}, _api2.default.getParticipationsByActivityId, {
                        url: url
                      });
                      _context.next = 8;
                      return (0, _utils.fetchAPI)(_params);

                    case 8:
                      res = _context.sent;


                      console.log('getParticipationsByActivityId response: ', res);

                      return _context.abrupt('return', res);

                    case 11:
                    case 'end':
                      return _context.stop();
                  }
                }
              }, _callee, this);
            }))());

          case 1:
          case 'end':
            return _context2.stop();
        }
      }
    }, _callee2, this);
  }));

  function getParticipationsByActivityId() {
    return _ref.apply(this, arguments);
  }

  return getParticipationsByActivityId;
}();

/***/ }),
/* 69 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _regenerator = __webpack_require__(0);

var _regenerator2 = _interopRequireDefault(_regenerator);

var _assign = __webpack_require__(4);

var _assign2 = _interopRequireDefault(_assign);

var _asyncToGenerator2 = __webpack_require__(1);

var _asyncToGenerator3 = _interopRequireDefault(_asyncToGenerator2);

var _utils = __webpack_require__(2);

var _api = __webpack_require__(8);

var _api2 = _interopRequireDefault(_api);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

/**
 * the response likes below:
 * {
"activityId": 0,
"avatar": "string",
"id": 0,
"nickName": "string",
"participationTime": "2018-06-19T16:24:33.335Z",
"project": "string",
"wechatUserId": "string"
}
 *
 *
 */
exports.default = function () {
  var _ref = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee() {
    var params = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : {};

    var url, _params, res;

    return _regenerator2.default.wrap(function _callee$(_context) {
      while (1) {
        switch (_context.prev = _context.next) {
          case 0:
            /**
             * 
            params = {
            "activityId": 0,
            "avatar": "string",
            "id": 0,
            "nickName": "string",
            "participationTime": "2018-06-19T16:24:33.343Z",
            "project": "string",
            "wechatUserId": "string"
            }
            **/
            url = _api2.default.createActivityParticipation.url;
            _params = (0, _assign2.default)({}, _api2.default.createActivityParticipation, {
              url: url,
              body: params
            });
            _context.next = 4;
            return (0, _utils.fetchAPI)(_params);

          case 4:
            res = _context.sent;


            console.log('createActivityParticipation response: ', res);

            return _context.abrupt('return', res);

          case 7:
          case 'end':
            return _context.stop();
        }
      }
    }, _callee, this);
  }));

  function createActivityParticipation() {
    return _ref.apply(this, arguments);
  }

  return createActivityParticipation;
}();

/***/ }),
/* 70 */,
/* 71 */,
/* 72 */,
/* 73 */,
/* 74 */,
/* 75 */,
/* 76 */,
/* 77 */,
/* 78 */,
/* 79 */,
/* 80 */,
/* 81 */,
/* 82 */,
/* 83 */,
/* 84 */,
/* 85 */,
/* 86 */,
/* 87 */,
/* 88 */,
/* 89 */,
/* 90 */,
/* 91 */,
/* 92 */,
/* 93 */,
/* 94 */,
/* 95 */,
/* 96 */,
/* 97 */,
/* 98 */,
/* 99 */,
/* 100 */,
/* 101 */,
/* 102 */,
/* 103 */,
/* 104 */,
/* 105 */,
/* 106 */,
/* 107 */,
/* 108 */,
/* 109 */,
/* 110 */,
/* 111 */,
/* 112 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _footer = __webpack_require__(42);

var _footer2 = _interopRequireDefault(_footer);

var _spin = __webpack_require__(29);

var _spin2 = _interopRequireDefault(_spin);

var _imagePreview = __webpack_require__(113);

var _imagePreview2 = _interopRequireDefault(_imagePreview);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.default = {
  name: 'app',
  components: {
    Foot: _footer2.default,
    Spin: _spin2.default,
    imagePreview: _imagePreview2.default
  },
  data: function data() {
    return {
      list: [{
        icon: 'ios-home-outline',
        name: '首页',
        url: '/',
        meta: ''
      }, {
        icon: 'ios-plus-outline',
        name: '发布',
        url: '/publish',
        meta: ''
      }, {
        icon: 'ios-person-outline',
        name: '我的',
        url: '/user',
        meta: ''
      }],
      isLoading: false,
      spinText: '请稍后',
      hasPublished: false,
      hasJoined: false,
      isImagePreview: false,
      imageList: [],
      imagePreviewPos: 0
    };
  },

  methods: {
    switchLoading: function switchLoading() {
      var status = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : false;
      var text = arguments.length > 1 && arguments[1] !== undefined ? arguments[1] : '请稍后';

      this.isLoading = status;
      this.spinText = text;
    },
    openImagePreview: function openImagePreview() {
      var status = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : true;
      var imageList = arguments[1];
      var pos = arguments.length > 2 && arguments[2] !== undefined ? arguments[2] : 0;

      this.isImagePreview = status;
      this.imageList = imageList;
      this.imagePreviewPos = pos;
    }
  },
  created: function created() {},
  mounted: function mounted() {}
}; //
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

/***/ }),
/* 113 */,
/* 114 */,
/* 115 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _regenerator = __webpack_require__(0);

var _regenerator2 = _interopRequireDefault(_regenerator);

var _asyncToGenerator2 = __webpack_require__(1);

var _asyncToGenerator3 = _interopRequireDefault(_asyncToGenerator2);

var _swiper = __webpack_require__(61);

var _swiper2 = _interopRequireDefault(_swiper);

var _iview = __webpack_require__(7);

var _utils = __webpack_require__(2);

var _popularActivity = __webpack_require__(116);

var _popularActivity2 = _interopRequireDefault(_popularActivity);

var _getAllFitnessActivities2 = __webpack_require__(118);

var _getAllFitnessActivities3 = _interopRequireDefault(_getAllFitnessActivities2);

var _getActivitiesByWechatUserId2 = __webpack_require__(119);

var _getActivitiesByWechatUserId3 = _interopRequireDefault(_getActivitiesByWechatUserId2);

var _getClockinSummaryByWechatId2 = __webpack_require__(314);

var _getClockinSummaryByWechatId3 = _interopRequireDefault(_getClockinSummaryByWechatId2);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.default = {
  name: 'index',
  components: {
    Row: _iview.Row,
    Col: _iview.Col,
    Icon: _iview.Icon,
    popularActivity: _popularActivity2.default
  },
  data: function data() {
    return {
      isLoaded: false,
      activities: [],
      userInfo: wechatUser,
      myActivities: [],
      summary: {
        serialCount: 0,
        totallyCount: 0,
        weeklyCount: 0
      }
    };
  },

  methods: {
    init: function () {
      var _ref = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee() {
        return _regenerator2.default.wrap(function _callee$(_context) {
          while (1) {
            switch (_context.prev = _context.next) {
              case 0:
                this.$root.$children[0].switchLoading(true, '查询中');
                _context.next = 3;
                return this.getClockinSummaryByWechatId();

              case 3:
                _context.next = 5;
                return this.getAllFitnessActivities();

              case 5:
                _context.next = 7;
                return this.getActivitiesByWechatUserId();

              case 7:
                this.isLoaded = true;
                this.$root.$children[0].switchLoading(false);

              case 9:
              case 'end':
                return _context.stop();
            }
          }
        }, _callee, this);
      }));

      function init() {
        return _ref.apply(this, arguments);
      }

      return init;
    }(),
    getClockinSummaryByWechatId: function () {
      var _ref2 = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee2() {
        var res;
        return _regenerator2.default.wrap(function _callee2$(_context2) {
          while (1) {
            switch (_context2.prev = _context2.next) {
              case 0:
                _context2.next = 2;
                return (0, _getClockinSummaryByWechatId3.default)({ wechatUserId: wechatUser.wechatUserId });

              case 2:
                res = _context2.sent;

                if (res) {
                  this.summary = res;
                }

              case 4:
              case 'end':
                return _context2.stop();
            }
          }
        }, _callee2, this);
      }));

      function getClockinSummaryByWechatId() {
        return _ref2.apply(this, arguments);
      }

      return getClockinSummaryByWechatId;
    }(),
    getAllFitnessActivities: function () {
      var _ref3 = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee3() {
        var res;
        return _regenerator2.default.wrap(function _callee3$(_context3) {
          while (1) {
            switch (_context3.prev = _context3.next) {
              case 0:
                _context3.next = 2;
                return (0, _getAllFitnessActivities3.default)({
                  page: 0,
                  size: 6,
                  sort: ['modifyTime', 'desc']
                });

              case 2:
                res = _context3.sent;

                this.activities = res;

              case 4:
              case 'end':
                return _context3.stop();
            }
          }
        }, _callee3, this);
      }));

      function getAllFitnessActivities() {
        return _ref3.apply(this, arguments);
      }

      return getAllFitnessActivities;
    }(),
    getActivitiesByWechatUserId: function () {
      var _ref4 = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee4() {
        var res;
        return _regenerator2.default.wrap(function _callee4$(_context4) {
          while (1) {
            switch (_context4.prev = _context4.next) {
              case 0:
                _context4.next = 2;
                return (0, _getActivitiesByWechatUserId3.default)({
                  wechatUserId: wechatUser.wechatUserId,
                  sort: ['id', 'desc']
                });

              case 2:
                res = _context4.sent;

                this.myActivities = res;

              case 4:
              case 'end':
                return _context4.stop();
            }
          }
        }, _callee4, this);
      }));

      function getActivitiesByWechatUserId() {
        return _ref4.apply(this, arguments);
      }

      return getActivitiesByWechatUserId;
    }(),
    gotoDetail: function gotoDetail(id) {
      this.$router.push({ path: 'detail/' + id });
    },
    goAnchor: function goAnchor(selector) {
      var anchor = this.$el.querySelector(selector);
      document.documentElement.scrollTop = anchor.offsetTop;
      document.body.scrollTop = anchor.offsetTop;
    },
    createSwiper: function createSwiper() {
      var mySwiper = new _swiper2.default('.swiper-container', {
        loop: true,
        autoplay: true,
        pagination: {
          el: '.swiper-pagination'
        },
        navigation: {
          nextEl: '.swiper-button-next',
          prevEl: '.swiper-button-prev'
        },
        scrollbar: {
          el: '.swiper-scrollbar'
        },
        paginationClickable: true,
        observer: true, //修改swiper自己或子元素时，自动初始化swiper
        observeParents: true //修改swiper的父元素时，自动初始化swiper
      });
    }
  },
  mounted: function mounted() {
    document.documentElement.scrollTop = 0;
    this.init();
    this.createSwiper();
  },
  activated: function activated() {
    if (this.$root.$children[0].hasPublished === true) {
      this.init();
      this.$root.$children[0].hasPublished = false;
    }
    if (this.$root.$children[0].hasJoined === true) {
      this.getActivitiesByWechatUserId();
      this.$root.$children[0].hasJoined = false;
    }
  }
}; //
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

/***/ }),
/* 116 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_popularActivity_vue__ = __webpack_require__(117);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_popularActivity_vue___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_popularActivity_vue__);
/* harmony namespace reexport (unknown) */ for(var __WEBPACK_IMPORT_KEY__ in __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_popularActivity_vue__) if(__WEBPACK_IMPORT_KEY__ !== 'default') (function(key) { __webpack_require__.d(__webpack_exports__, key, function() { return __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_popularActivity_vue__[key]; }) }(__WEBPACK_IMPORT_KEY__));
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_vue_loader_lib_template_compiler_index_id_data_v_00e9152c_hasScoped_true_optionsId_0_buble_transforms_node_modules_vue_loader_lib_selector_type_template_index_0_popularActivity_vue__ = __webpack_require__(312);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__node_modules_vue_loader_lib_runtime_component_normalizer__ = __webpack_require__(6);
var disposed = false
function injectStyle (context) {
  if (disposed) return
  __webpack_require__(310)
}
/* script */


/* template */

/* template functional */
var __vue_template_functional__ = false
/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-00e9152c"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null

var Component = Object(__WEBPACK_IMPORTED_MODULE_2__node_modules_vue_loader_lib_runtime_component_normalizer__["a" /* default */])(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_popularActivity_vue___default.a,
  __WEBPACK_IMPORTED_MODULE_1__node_modules_vue_loader_lib_template_compiler_index_id_data_v_00e9152c_hasScoped_true_optionsId_0_buble_transforms_node_modules_vue_loader_lib_selector_type_template_index_0_popularActivity_vue__["a" /* render */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_vue_loader_lib_template_compiler_index_id_data_v_00e9152c_hasScoped_true_optionsId_0_buble_transforms_node_modules_vue_loader_lib_selector_type_template_index_0_popularActivity_vue__["b" /* staticRenderFns */],
  __vue_template_functional__,
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "dev\\components\\fit\\popularActivity.vue"

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-00e9152c", Component.options)
  } else {
    hotAPI.reload("data-v-00e9152c", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["default"] = (Component.exports);


/***/ }),
/* 117 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

exports.default = {
  name: 'popular-activity',
  data: function data() {
    return {};
  },

  props: {
    activities: Array
  },
  methods: {
    gotoDetail: function gotoDetail(id) {
      this.$router.push({ path: 'detail/' + id });
    }
  },
  mounted: function mounted() {
    console.log(this.activities);
  }
};

/***/ }),
/* 118 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _regenerator = __webpack_require__(0);

var _regenerator2 = _interopRequireDefault(_regenerator);

var _assign = __webpack_require__(4);

var _assign2 = _interopRequireDefault(_assign);

var _asyncToGenerator2 = __webpack_require__(1);

var _asyncToGenerator3 = _interopRequireDefault(_asyncToGenerator2);

var _utils = __webpack_require__(2);

var _api = __webpack_require__(8);

var _api2 = _interopRequireDefault(_api);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

/**
 * the response likes below:
 * [
{
  "activityEndTime": "2018-06-19T16:24:33.491Z",
  "activityStartTime": "2018-06-19T16:24:33.491Z",
  "avatar": "string",
  "companyRole": "string",
  "descrption": "string",
  "id": 0,
  "nickName": "string",
  "project": "string",
  "signEndTime": "2018-06-19T16:24:33.491Z",
  "signStartTime": "2018-06-19T16:24:33.491Z",
  "title": "string",
  "wechatUserId": "string"
}
]
 *
 *
 */
exports.default = function () {
  var _ref = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee(params) {
    var page, size, sort, _search, url, _params, res;

    return _regenerator2.default.wrap(function _callee$(_context) {
      while (1) {
        switch (_context.prev = _context.next) {
          case 0:
            page = params.page, size = params.size, sort = params.sort;
            _search = (0, _utils.joinParams)(params);
            url = _api2.default.getAllFitnessActivities.url + '/?' + _search;
            _params = (0, _assign2.default)({}, _api2.default.getAllFitnessActivities, {
              url: url
            });
            _context.next = 6;
            return (0, _utils.fetchAPI)(_params);

          case 6:
            res = _context.sent;


            console.log('getAllFitnessActivities response: ', res);

            return _context.abrupt('return', res);

          case 9:
          case 'end':
            return _context.stop();
        }
      }
    }, _callee, this);
  }));

  function getAllFitnessActivities(_x) {
    return _ref.apply(this, arguments);
  }

  return getAllFitnessActivities;
}();

/***/ }),
/* 119 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _regenerator = __webpack_require__(0);

var _regenerator2 = _interopRequireDefault(_regenerator);

var _assign = __webpack_require__(4);

var _assign2 = _interopRequireDefault(_assign);

var _asyncToGenerator2 = __webpack_require__(1);

var _asyncToGenerator3 = _interopRequireDefault(_asyncToGenerator2);

var _utils = __webpack_require__(2);

var _api = __webpack_require__(8);

var _api2 = _interopRequireDefault(_api);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

/*{
  "activityId": 0,
  "avatar": "string",
  "clockinCount": 0,
  "id": 0,
  "nickName": "string",
  "participationTime": "string",
  "project": "string",
  "wechatUserId": "string"
}*/

exports.default = function () {
  var _ref = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee2() {
    var params = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : {
      wechatUserId: wechatUserId,
      sort: sort
    };
    return _regenerator2.default.wrap(function _callee2$(_context2) {
      while (1) {
        switch (_context2.prev = _context2.next) {
          case 0:
            return _context2.abrupt('return', (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee() {
              var wechatUserId, sort, _search, url, _params, res;

              return _regenerator2.default.wrap(function _callee$(_context) {
                while (1) {
                  switch (_context.prev = _context.next) {
                    case 0:
                      wechatUserId = params.wechatUserId, sort = params.sort;

                      if (wechatUserId) {
                        _context.next = 3;
                        break;
                      }

                      return _context.abrupt('return');

                    case 3:
                      _search = (0, _utils.joinParams)(params);
                      url = _api2.default.getActivitiesByWechatUserId.url + '/?' + _search;
                      _params = (0, _assign2.default)({}, _api2.default.getActivitiesByWechatUserId, {
                        url: url
                      });
                      _context.next = 8;
                      return (0, _utils.fetchAPI)(_params);

                    case 8:
                      res = _context.sent;


                      console.log('getActivitiesByWechatUserId response: ', res);

                      return _context.abrupt('return', res);

                    case 11:
                    case 'end':
                      return _context.stop();
                  }
                }
              }, _callee, this);
            }))());

          case 1:
          case 'end':
            return _context2.stop();
        }
      }
    }, _callee2, this);
  }));

  function getActivitiesByWechatUserId() {
    return _ref.apply(this, arguments);
  }

  return getActivitiesByWechatUserId;
}();

/***/ }),
/* 120 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _getIterator2 = __webpack_require__(13);

var _getIterator3 = _interopRequireDefault(_getIterator2);

var _regenerator = __webpack_require__(0);

var _regenerator2 = _interopRequireDefault(_regenerator);

var _asyncToGenerator2 = __webpack_require__(1);

var _asyncToGenerator3 = _interopRequireDefault(_asyncToGenerator2);

var _iview = __webpack_require__(7);

var _deleteActivityParticipation = __webpack_require__(321);

var _deleteActivityParticipation2 = _interopRequireDefault(_deleteActivityParticipation);

var _deleteFitnessActivity = __webpack_require__(322);

var _deleteFitnessActivity2 = _interopRequireDefault(_deleteFitnessActivity);

var _getFitnessActicity = __webpack_require__(67);

var _getFitnessActicity2 = _interopRequireDefault(_getFitnessActicity);

var _getActivityParticipation2 = __webpack_require__(121);

var _getActivityParticipation3 = _interopRequireDefault(_getActivityParticipation2);

var _getParticipationsByActivityId2 = __webpack_require__(68);

var _getParticipationsByActivityId3 = _interopRequireDefault(_getParticipationsByActivityId2);

var _createActivityParticipation = __webpack_require__(69);

var _createActivityParticipation2 = _interopRequireDefault(_createActivityParticipation);

var _swiper = __webpack_require__(60);

var _swiper2 = _interopRequireDefault(_swiper);

var _attendee = __webpack_require__(62);

var _attendee2 = _interopRequireDefault(_attendee);

var _BigImg = __webpack_require__(122);

var _BigImg2 = _interopRequireDefault(_BigImg);

var _comments = __webpack_require__(63);

var _comments2 = _interopRequireDefault(_comments);

var _getComments2 = __webpack_require__(124);

var _getComments3 = _interopRequireDefault(_getComments2);

var _utils = __webpack_require__(2);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.default = {
  name: "detail",
  components: {
    Icon: _iview.Icon,
    Row: _iview.Row,
    Col: _iview.Col,
    Swiper: _swiper2.default,
    Attendee: _attendee2.default,
    Comments: _comments2.default,
    'big-img': _BigImg2.default
  },
  filters: {
    decodeBase64: function decodeBase64(val) {
      return val ? (0, _utils.decodeBase64)(val) : val;
    }
  },
  data: function data() {
    return {
      isLoaded: false,
      showImg: false,
      imgSrc: '',
      isMine: false,
      activity: {
        title: "",
        number: 4,
        nickName: '',
        companyRole: '',
        avatar: '',
        project: '',
        signStartTime: '',
        signEndTime: '',
        activityStartTime: '',
        activityEndTime: ''
      },
      activityId: this.$route.params.id,
      join: [],
      isNotJoined: true,
      images: [],
      userInfo: wechatUser,
      overdue: 0,
      canClick: true,
      list: '',
      personId: ''
    };
  },

  methods: {
    /**
     * 
     */
    init: function () {
      var _ref = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee() {
        return _regenerator2.default.wrap(function _callee$(_context) {
          while (1) {
            switch (_context.prev = _context.next) {
              case 0:
                this.$root.$children[0].switchLoading(true, '活动查询中');
                _context.next = 3;
                return this.getFitnessActivity();

              case 3:
                _context.next = 5;
                return this.getActivityParticipation();

              case 5:
                _context.next = 7;
                return this.getParticipationsByActivityId();

              case 7:
                this.$root.$children[0].switchLoading(false);
                this.isLoaded = true;

              case 9:
              case 'end':
                return _context.stop();
            }
          }
        }, _callee, this);
      }));

      function init() {
        return _ref.apply(this, arguments);
      }

      return init;
    }(),
    /**
     * 
     */
    getFitnessActivity: function () {
      var _ref2 = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee2() {
        var res, _iteratorNormalCompletion, _didIteratorError, _iteratorError, _iterator, _step, item;

        return _regenerator2.default.wrap(function _callee2$(_context2) {
          while (1) {
            switch (_context2.prev = _context2.next) {
              case 0:
                _context2.next = 2;
                return (0, _getFitnessActicity2.default)(this.activityId);

              case 2:
                res = _context2.sent;

                console.log(res);
                this.activity = res;
                this.overdue = res.status;
                if (res.wechatUserId == wechatUser.wechatUserId) {
                  this.isMine = true;
                }
                // 0 :活动未开始。  1：活动进行中。 2 ：活动已结束
                // 后台接口图片返回格式不统一，前台自己拼格式

                if (!res.images.length) {
                  _context2.next = 27;
                  break;
                }

                _iteratorNormalCompletion = true;
                _didIteratorError = false;
                _iteratorError = undefined;
                _context2.prev = 11;

                for (_iterator = (0, _getIterator3.default)(res.images); !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
                  item = _step.value;

                  this.images.push(item.ossPath);
                }
                _context2.next = 19;
                break;

              case 15:
                _context2.prev = 15;
                _context2.t0 = _context2['catch'](11);
                _didIteratorError = true;
                _iteratorError = _context2.t0;

              case 19:
                _context2.prev = 19;
                _context2.prev = 20;

                if (!_iteratorNormalCompletion && _iterator.return) {
                  _iterator.return();
                }

              case 22:
                _context2.prev = 22;

                if (!_didIteratorError) {
                  _context2.next = 25;
                  break;
                }

                throw _iteratorError;

              case 25:
                return _context2.finish(22);

              case 26:
                return _context2.finish(19);

              case 27:
              case 'end':
                return _context2.stop();
            }
          }
        }, _callee2, this, [[11, 15, 19, 27], [20,, 22, 26]]);
      }));

      function getFitnessActivity() {
        return _ref2.apply(this, arguments);
      }

      return getFitnessActivity;
    }(),
    /**
     * 
     */
    getActivityParticipation: function () {
      var _ref3 = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee3() {
        var res;
        return _regenerator2.default.wrap(function _callee3$(_context3) {
          while (1) {
            switch (_context3.prev = _context3.next) {
              case 0:
                _context3.next = 2;
                return (0, _getActivityParticipation3.default)(wechatUser.wechatUserId, this.activityId);

              case 2:
                res = _context3.sent;

                this.isNotJoined = res ? false : true;
                console.log(this.isNotJoined);

              case 5:
              case 'end':
                return _context3.stop();
            }
          }
        }, _callee3, this);
      }));

      function getActivityParticipation() {
        return _ref3.apply(this, arguments);
      }

      return getActivityParticipation;
    }(),
    /**
     * 
     */
    getParticipationsByActivityId: function () {
      var _ref4 = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee4() {
        var res, i;
        return _regenerator2.default.wrap(function _callee4$(_context4) {
          while (1) {
            switch (_context4.prev = _context4.next) {
              case 0:
                _context4.next = 2;
                return (0, _getParticipationsByActivityId3.default)({ activityId: this.activityId });

              case 2:
                res = _context4.sent;

                this.join = res;
                for (i = 0; i < res.length; i++) {
                  if (res[i].wechatUserId == wechatUser.wechatUserId) {
                    this.personId = res[i].id;
                  }
                }
                console.log('join', res);

              case 6:
              case 'end':
                return _context4.stop();
            }
          }
        }, _callee4, this);
      }));

      function getParticipationsByActivityId() {
        return _ref4.apply(this, arguments);
      }

      return getParticipationsByActivityId;
    }(),
    /**
     * 
     */
    joinActivities: function () {
      var _ref5 = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee5() {
        var res;
        return _regenerator2.default.wrap(function _callee5$(_context5) {
          while (1) {
            switch (_context5.prev = _context5.next) {
              case 0:
                if (this.canClick) {
                  _context5.next = 2;
                  break;
                }

                return _context5.abrupt('return');

              case 2:

                this.$root.$children[0].switchLoading(true, '报名中');
                _context5.next = 5;
                return (0, _createActivityParticipation2.default)({
                  activityId: this.activity.id,
                  wechatUserId: wechatUser.wechatUserId
                });

              case 5:
                res = _context5.sent;

                this.$root.$children[0].switchLoading(false);

                if (res.id) {
                  (0, _utils.showMessage)('success', '报名成功');
                  this.canClick = false;
                  this.isNotJoined = false;
                  this.$root.$children[0].hasJoined = true;
                } else if (res.status === 400) {
                  (0, _utils.showMessage)('error', res.errorKey);
                }

              case 8:
              case 'end':
                return _context5.stop();
            }
          }
        }, _callee5, this);
      }));

      function joinActivities() {
        return _ref5.apply(this, arguments);
      }

      return joinActivities;
    }(),
    delYear: function delYear(date) {
      return (0, _utils.delYear)(date);
    },
    formatDate: function formatDate(date) {
      return (0, _utils.formatDate)(date);
    },
    clickImg: function clickImg(e) {
      this.showImg = true;
      // 获取当前图片地址
      this.imgSrc = e.currentTarget.src;
    },
    viewImg: function viewImg() {
      this.showImg = false;
    },

    /**
     * 
     */
    getComments: function () {
      var _ref6 = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee6(id) {
        var res;
        return _regenerator2.default.wrap(function _callee6$(_context6) {
          while (1) {
            switch (_context6.prev = _context6.next) {
              case 0:
                this.$root.$children[0].switchLoading(true, '加载讨论');
                _context6.next = 3;
                return (0, _getComments3.default)({
                  channel: 'FIT',
                  objectId: id,
                  page: 0,
                  size: 200,
                  sort: ['createTime', 'desc']
                });

              case 3:
                res = _context6.sent;

                this.$root.$children[0].switchLoading(false);

                if (res) {
                  _context6.next = 7;
                  break;
                }

                return _context6.abrupt('return');

              case 7:

                this.list = res;

              case 8:
              case 'end':
                return _context6.stop();
            }
          }
        }, _callee6, this);
      }));

      function getComments(_x) {
        return _ref6.apply(this, arguments);
      }

      return getComments;
    }(),
    /**
     * 
     */
    cancelApply: function () {
      var _ref7 = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee7() {
        var _this = this;

        var res;
        return _regenerator2.default.wrap(function _callee7$(_context7) {
          while (1) {
            switch (_context7.prev = _context7.next) {
              case 0:
                _context7.next = 2;
                return (0, _deleteActivityParticipation2.default)(this.personId);

              case 2:
                res = _context7.sent;

                console.log(res);
                if (res.status === 500) {
                  (0, _utils.showMessage)('error', res.title);
                } else {
                  this.isShowTips = false;
                  (0, _utils.showMessage)('success', '取消成功', function () {
                    _this.$router.push('/user');
                  });
                }

              case 5:
              case 'end':
                return _context7.stop();
            }
          }
        }, _callee7, this);
      }));

      function cancelApply() {
        return _ref7.apply(this, arguments);
      }

      return cancelApply;
    }(),

    /**
     * 
     */
    cancelActivity: function () {
      var _ref8 = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee8() {
        var _this2 = this;

        var res;
        return _regenerator2.default.wrap(function _callee8$(_context8) {
          while (1) {
            switch (_context8.prev = _context8.next) {
              case 0:
                _context8.next = 2;
                return (0, _deleteFitnessActivity2.default)(this.activity.id);

              case 2:
                res = _context8.sent;

                (0, _utils.showMessage)('success', '取消成功', function () {
                  _this2.$router.push('/user');
                });

              case 4:
              case 'end':
                return _context8.stop();
            }
          }
        }, _callee8, this);
      }));

      function cancelActivity() {
        return _ref8.apply(this, arguments);
      }

      return cancelActivity;
    }(),

    /**
     * 
     */
    editActivity: function editActivity() {

      this.$router.push('/publish/' + this.activity.id);
    }
  },
  mounted: function mounted() {
    this.init();
    this.id = this.$route.params.id;
    this.getComments(this.id);
  }
}; //
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

/***/ }),
/* 121 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _regenerator = __webpack_require__(0);

var _regenerator2 = _interopRequireDefault(_regenerator);

var _assign = __webpack_require__(4);

var _assign2 = _interopRequireDefault(_assign);

var _asyncToGenerator2 = __webpack_require__(1);

var _asyncToGenerator3 = _interopRequireDefault(_asyncToGenerator2);

var _utils = __webpack_require__(2);

var _api = __webpack_require__(8);

var _api2 = _interopRequireDefault(_api);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

/**
 * the response likes below:
 * {
"activityId": 0,
"avatar": "string",
"id": 0,
"nickName": "string",
"participationTime": "2018-06-19T16:24:33.361Z",
"project": "string",
"wechatUserId": "string"
}
 *
 *
 */
exports.default = function () {
  var _ref = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee(wechatUserId, activityId) {
    var url, _params, res;

    return _regenerator2.default.wrap(function _callee$(_context) {
      while (1) {
        switch (_context.prev = _context.next) {
          case 0:
            url = _api2.default.getActivityParticipation.url.replace('{{wechatUserId}}', wechatUserId).replace('{{activityId}}', activityId);
            _params = (0, _assign2.default)({}, _api2.default.getActivityParticipation, {
              url: url
            });
            _context.next = 4;
            return (0, _utils.fetchAPI)(_params);

          case 4:
            res = _context.sent;


            console.log('getActivityParticipation response: ', res);

            return _context.abrupt('return', res);

          case 7:
          case 'end':
            return _context.stop();
        }
      }
    }, _callee, this);
  }));

  function getActivityParticipation(_x, _x2) {
    return _ref.apply(this, arguments);
  }

  return getActivityParticipation;
}();

/***/ }),
/* 122 */,
/* 123 */,
/* 124 */,
/* 125 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _regenerator = __webpack_require__(0);

var _regenerator2 = _interopRequireDefault(_regenerator);

var _asyncToGenerator2 = __webpack_require__(1);

var _asyncToGenerator3 = _interopRequireDefault(_asyncToGenerator2);

var _getIterator2 = __webpack_require__(13);

var _getIterator3 = _interopRequireDefault(_getIterator2);

var _iview = __webpack_require__(7);

var _modal = __webpack_require__(26);

var _modal2 = _interopRequireDefault(_modal);

var _upload = __webpack_require__(14);

var _upload2 = _interopRequireDefault(_upload);

var _createFitnessActivity2 = __webpack_require__(126);

var _createFitnessActivity3 = _interopRequireDefault(_createFitnessActivity2);

var _createActivityParticipation = __webpack_require__(69);

var _createActivityParticipation2 = _interopRequireDefault(_createActivityParticipation);

var _viewCreateFitnessActivity = __webpack_require__(330);

var _viewCreateFitnessActivity2 = _interopRequireDefault(_viewCreateFitnessActivity);

var _utils = __webpack_require__(2);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.default = {
  name: 'publish',
  components: {
    Icon: _iview.Icon,
    Upload: _upload2.default,
    Modal: _modal2.default
  },
  data: function data() {
    return {
      title: '',
      signStartTime: '',
      signEndTime: '',
      activityStartTime: '2018-07-11',
      activityEndTime: '',
      description: '',
      userInfo: wechatUser,
      lastDay: '',
      dialog: ''
    };
  },

  methods: {
    //上传
    submit: function submit() {
      if (!this.title || !this.description || !this.$refs.upload.files.length) {
        this.dialog = "请输入完整的相关信息";
        this.$refs.modal.isOpen = true;
        return;
      }

      if ((0, _utils.isEarlierThanToday)(this.activityStartTime, 1)) {
        this.dialog = "活动开始时间需大于当前日期";
        this.$refs.modal.isOpen = true;
        return;
      }

      this.$refs.upload.$refs.startUpload.click();
    },

    // 图片上传完成
    finishUpload: function finishUpload() {
      console.log(this.$refs.upload.ossPath);
      var images = [];
      var _iteratorNormalCompletion = true;
      var _didIteratorError = false;
      var _iteratorError = undefined;

      try {
        for (var _iterator = (0, _getIterator3.default)(this.$refs.upload.ossPath), _step; !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
          var item = _step.value;

          images.push({
            ossPath: item
          });
        }
      } catch (err) {
        _didIteratorError = true;
        _iteratorError = err;
      } finally {
        try {
          if (!_iteratorNormalCompletion && _iterator.return) {
            _iterator.return();
          }
        } finally {
          if (_didIteratorError) {
            throw _iteratorError;
          }
        }
      }

      this.createFitnessActivity({
        activityEndTime: (0, _utils.addLastTime)(this.activityStartTime, this.lastDay),
        activityStartTime: this.activityStartTime + ' ' + '00:00:00',
        attendCount: 0,
        descrption: this.description,
        images: images,
        title: this.title,
        wechatUserId: this.userInfo.wechatUserId
      });
    },

    createFitnessActivity: function () {
      var _ref = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee(params) {
        var _this = this;

        var res;
        return _regenerator2.default.wrap(function _callee$(_context) {
          while (1) {
            switch (_context.prev = _context.next) {
              case 0:
                this.$root.$children[0].switchLoading(true, '活动发布中');
                _context.next = 3;
                return (0, _createFitnessActivity3.default)(params);

              case 3:
                res = _context.sent;

                this.$root.$children[0].switchLoading(false);

                this.joinActivities(res.id);

                (0, _utils.showMessage)('success', '发布成功', function () {
                  _this.$root.$children[0].hasPublished = true;
                  _this.$router.replace('/detail/' + res.id);
                });

              case 7:
              case 'end':
                return _context.stop();
            }
          }
        }, _callee, this);
      }));

      function createFitnessActivity(_x) {
        return _ref.apply(this, arguments);
      }

      return createFitnessActivity;
    }(),
    joinActivities: function () {
      var _ref2 = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee2(activityId) {
        var post, res;
        return _regenerator2.default.wrap(function _callee2$(_context2) {
          while (1) {
            switch (_context2.prev = _context2.next) {
              case 0:
                post = {
                  activityId: activityId,
                  wechatUserId: this.userInfo.wechatUserId
                };
                _context2.next = 3;
                return (0, _createActivityParticipation2.default)(post);

              case 3:
                res = _context2.sent;

              case 4:
              case 'end':
                return _context2.stop();
            }
          }
        }, _callee2, this);
      }));

      function joinActivities(_x2) {
        return _ref2.apply(this, arguments);
      }

      return joinActivities;
    }()
  },
  mounted: function mounted() {
    (0, _viewCreateFitnessActivity2.default)(wechatUser.wechatUserId);
  }
}; //
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

/***/ }),
/* 126 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _regenerator = __webpack_require__(0);

var _regenerator2 = _interopRequireDefault(_regenerator);

var _assign = __webpack_require__(4);

var _assign2 = _interopRequireDefault(_assign);

var _asyncToGenerator2 = __webpack_require__(1);

var _asyncToGenerator3 = _interopRequireDefault(_asyncToGenerator2);

var _utils = __webpack_require__(2);

var _api = __webpack_require__(8);

var _api2 = _interopRequireDefault(_api);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

/**
 * the response likes below:
 * {
"activityEndTime": "2018-06-19T16:24:33.499Z",
"activityStartTime": "2018-06-19T16:24:33.499Z",
"avatar": "string",
"companyRole": "string",
"descrption": "string",
"id": 0,
"nickName": "string",
"project": "string",
"signEndTime": "2018-06-19T16:24:33.500Z",
"signStartTime": "2018-06-19T16:24:33.500Z",
"title": "string",
"wechatUserId": "string"
}
 *
 *
 */
exports.default = function () {
  var _ref = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee() {
    var params = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : {};

    var url, _params, res;

    return _regenerator2.default.wrap(function _callee$(_context) {
      while (1) {
        switch (_context.prev = _context.next) {
          case 0:
            /**
             * 
            params = {
            "activityEndTime": "2018-06-19T16:24:33.504Z",
            "activityStartTime": "2018-06-19T16:24:33.504Z",
            "avatar": "string",
            "companyRole": "string",
            "descrption": "string",
            "id": 0,
            "nickName": "string",
            "project": "string",
            "signEndTime": "2018-06-19T16:24:33.504Z",
            "signStartTime": "2018-06-19T16:24:33.504Z",
            "title": "string",
            "wechatUserId": "string"
            }
            **/
            url = _api2.default.createFitnessActivity.url;
            _params = (0, _assign2.default)({}, _api2.default.createFitnessActivity, {
              url: url,
              body: params
            });
            _context.next = 4;
            return (0, _utils.fetchAPI)(_params);

          case 4:
            res = _context.sent;


            console.log('createFitnessActivity response: ', res);

            return _context.abrupt('return', res);

          case 7:
          case 'end':
            return _context.stop();
        }
      }
    }, _callee, this);
  }));

  function createFitnessActivity() {
    return _ref.apply(this, arguments);
  }

  return createFitnessActivity;
}();

/***/ }),
/* 127 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _regenerator = __webpack_require__(0);

var _regenerator2 = _interopRequireDefault(_regenerator);

var _getIterator2 = __webpack_require__(13);

var _getIterator3 = _interopRequireDefault(_getIterator2);

var _asyncToGenerator2 = __webpack_require__(1);

var _asyncToGenerator3 = _interopRequireDefault(_asyncToGenerator2);

var _iview = __webpack_require__(7);

var _modal = __webpack_require__(26);

var _modal2 = _interopRequireDefault(_modal);

var _upload = __webpack_require__(14);

var _upload2 = _interopRequireDefault(_upload);

var _updateFitnessActivity2 = __webpack_require__(335);

var _updateFitnessActivity3 = _interopRequireDefault(_updateFitnessActivity2);

var _getFitnessActicity = __webpack_require__(67);

var _getFitnessActicity2 = _interopRequireDefault(_getFitnessActicity);

var _createFitnessActivity = __webpack_require__(126);

var _createFitnessActivity2 = _interopRequireDefault(_createFitnessActivity);

var _createActivityParticipation = __webpack_require__(69);

var _createActivityParticipation2 = _interopRequireDefault(_createActivityParticipation);

var _utils = __webpack_require__(2);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

exports.default = {
  name: 'publishModify',
  components: {
    Icon: _iview.Icon,
    Upload: _upload2.default,
    Modal: _modal2.default
  },
  data: function data() {
    return {
      title: '',
      signStartTime: '',
      signEndTime: '',
      activityStartTime: '',
      activityEndTime: '',
      description: '',
      userInfo: wechatUser,
      lastDay: '',
      dialog: '',
      pics: []
    };
  },

  methods: {
    getFitnessActivity: function () {
      var _ref = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee(id) {
        var res, _iteratorNormalCompletion, _didIteratorError, _iteratorError, _iterator, _step, item;

        return _regenerator2.default.wrap(function _callee$(_context) {
          while (1) {
            switch (_context.prev = _context.next) {
              case 0:
                _context.next = 2;
                return (0, _getFitnessActicity2.default)(id);

              case 2:
                res = _context.sent;

                console.log(res);
                this.activity = res;
                this.title = res.title;
                this.signStartTime = res.signStartTime;
                this.signEndTime = res.signEndTime;
                this.activityStartTime = res.activityStartTime.split(' ')[0];
                this.activityEndTime = res.activityEndTime;
                this.description = res.descrption;
                this.lastDay = (new Date(res.activityEndTime) - new Date(res.activityStartTime) + 1000) / 1000 / 24 / 60 / 60;
                this.id = res.id;
                // 0 :活动未开始。  1：活动进行中。 2 ：活动已结束
                // 后台接口图片返回格式不统一，前台自己拼格式

                if (!res.images.length) {
                  _context.next = 33;
                  break;
                }

                _iteratorNormalCompletion = true;
                _didIteratorError = false;
                _iteratorError = undefined;
                _context.prev = 17;

                for (_iterator = (0, _getIterator3.default)(res.images); !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
                  item = _step.value;

                  this.$refs["upload"].files.push({ src: item.ossPath });
                  this.pics.push({ ossPath: item.ossPath });
                }
                _context.next = 25;
                break;

              case 21:
                _context.prev = 21;
                _context.t0 = _context['catch'](17);
                _didIteratorError = true;
                _iteratorError = _context.t0;

              case 25:
                _context.prev = 25;
                _context.prev = 26;

                if (!_iteratorNormalCompletion && _iterator.return) {
                  _iterator.return();
                }

              case 28:
                _context.prev = 28;

                if (!_didIteratorError) {
                  _context.next = 31;
                  break;
                }

                throw _iteratorError;

              case 31:
                return _context.finish(28);

              case 32:
                return _context.finish(25);

              case 33:
              case 'end':
                return _context.stop();
            }
          }
        }, _callee, this, [[17, 21, 25, 33], [26,, 28, 32]]);
      }));

      function getFitnessActivity(_x) {
        return _ref.apply(this, arguments);
      }

      return getFitnessActivity;
    }(),
    //上传
    submit: function submit() {
      if (!this.title || !this.description || !this.$refs.upload.files.length) {
        this.dialog = "请输入完整的相关信息";
        this.$refs.modal.isOpen = true;
        return;
      }

      if ((0, _utils.isEarlierThanToday)(this.activityStartTime, 1)) {
        this.dialog = "活动开始时间需大于当前日期";
        this.$refs.modal.isOpen = true;
        return;
      }

      if (this.$refs.upload.filesLength) {
        this.$refs.upload.$refs.startUpload.click();
      } else {
        this.finishUpload();
      }
    },

    // 图片上传完成
    finishUpload: function finishUpload() {
      console.log(this.$refs.upload.ossPath);
      // let images = [];
      var _iteratorNormalCompletion2 = true;
      var _didIteratorError2 = false;
      var _iteratorError2 = undefined;

      try {
        for (var _iterator2 = (0, _getIterator3.default)(this.$refs.upload.ossPath), _step2; !(_iteratorNormalCompletion2 = (_step2 = _iterator2.next()).done); _iteratorNormalCompletion2 = true) {
          var item = _step2.value;

          this.pics.push({
            ossPath: item
          });
        }
        // console.log('param',this.activityStartTime, this.lastDay);
      } catch (err) {
        _didIteratorError2 = true;
        _iteratorError2 = err;
      } finally {
        try {
          if (!_iteratorNormalCompletion2 && _iterator2.return) {
            _iterator2.return();
          }
        } finally {
          if (_didIteratorError2) {
            throw _iteratorError2;
          }
        }
      }

      this.updateFitnessActivity({
        id: this.id,
        activityEndTime: (0, _utils.addLastTime)(this.activityStartTime, this.lastDay),
        activityStartTime: this.activityStartTime + ' ' + '00:00:00',
        attendCount: this.activity.attendCount,
        descrption: this.description,
        images: this.pics,
        title: this.title,
        status: 0,
        wechatUserId: this.userInfo.wechatUserId
      });
    },

    updateFitnessActivity: function () {
      var _ref2 = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee2(params) {
        var _this = this;

        var res;
        return _regenerator2.default.wrap(function _callee2$(_context2) {
          while (1) {
            switch (_context2.prev = _context2.next) {
              case 0:
                this.$root.$children[0].switchLoading(true, '活动发布中');
                _context2.next = 3;
                return (0, _updateFitnessActivity3.default)(params);

              case 3:
                res = _context2.sent;

                this.$root.$children[0].switchLoading(false);

                // this.joinActivities(res.id);

                (0, _utils.showMessage)('success', '修改成功', function () {
                  _this.$root.$children[0].hasPublished = true;
                  _this.$router.replace('/detail/' + _this.id);
                });

              case 6:
              case 'end':
                return _context2.stop();
            }
          }
        }, _callee2, this);
      }));

      function updateFitnessActivity(_x2) {
        return _ref2.apply(this, arguments);
      }

      return updateFitnessActivity;
    }()
  },
  mounted: function mounted() {
    var id = this.$route.params.id;
    this.getFitnessActivity(id);
  }
};

/***/ }),
/* 128 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _getIterator2 = __webpack_require__(13);

var _getIterator3 = _interopRequireDefault(_getIterator2);

var _regenerator = __webpack_require__(0);

var _regenerator2 = _interopRequireDefault(_regenerator);

var _asyncToGenerator2 = __webpack_require__(1);

var _asyncToGenerator3 = _interopRequireDefault(_asyncToGenerator2);

var _iview = __webpack_require__(7);

var _upload = __webpack_require__(14);

var _upload2 = _interopRequireDefault(_upload);

var _spin = __webpack_require__(29);

var _spin2 = _interopRequireDefault(_spin);

var _getFitnessActicity = __webpack_require__(67);

var _getFitnessActicity2 = _interopRequireDefault(_getFitnessActicity);

var _createClockIn2 = __webpack_require__(340);

var _createClockIn3 = _interopRequireDefault(_createClockIn2);

var _utils = __webpack_require__(2);

var _getParticipationsByActivityId2 = __webpack_require__(68);

var _getParticipationsByActivityId3 = _interopRequireDefault(_getParticipationsByActivityId2);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.default = {
  name: "check",
  components: {
    Input: _iview.Input,
    Upload: _upload2.default,
    Button: _iview.Button
  },
  data: function data() {
    return {
      activity: {},
      activityId: '',
      signNote: '',
      activityParticipationId: 0,
      userInfo: {}
    };
  },

  methods: {
    /**
     * 
     */
    getUser: function () {
      var _ref = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee() {
        return _regenerator2.default.wrap(function _callee$(_context) {
          while (1) {
            switch (_context.prev = _context.next) {
              case 0:
                this.userInfo = wechatUser;
                _context.next = 3;
                return this.$root.$children[0].switchLoading(true, '加载中');

              case 3:
                _context.next = 5;
                return this.getFitnessActivity();

              case 5:
                _context.next = 7;
                return this.getParticipationsByActivityId();

              case 7:
                this.$root.$children[0].switchLoading(false);

              case 8:
              case 'end':
                return _context.stop();
            }
          }
        }, _callee, this);
      }));

      function getUser() {
        return _ref.apply(this, arguments);
      }

      return getUser;
    }(),
    /**
     * 
     */
    getFitnessActivity: function () {
      var _ref2 = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee2() {
        var res;
        return _regenerator2.default.wrap(function _callee2$(_context2) {
          while (1) {
            switch (_context2.prev = _context2.next) {
              case 0:
                _context2.next = 2;
                return (0, _getFitnessActicity2.default)(this.activityId);

              case 2:
                res = _context2.sent;

                this.activity = res;

              case 4:
              case 'end':
                return _context2.stop();
            }
          }
        }, _callee2, this);
      }));

      function getFitnessActivity() {
        return _ref2.apply(this, arguments);
      }

      return getFitnessActivity;
    }(),
    /**
     * 
     */
    getParticipationsByActivityId: function () {
      var _ref3 = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee3() {
        var res, _iteratorNormalCompletion, _didIteratorError, _iteratorError, _iterator, _step, item;

        return _regenerator2.default.wrap(function _callee3$(_context3) {
          while (1) {
            switch (_context3.prev = _context3.next) {
              case 0:
                _context3.next = 2;
                return (0, _getParticipationsByActivityId3.default)({ activityId: this.activityId });

              case 2:
                res = _context3.sent;
                _iteratorNormalCompletion = true;
                _didIteratorError = false;
                _iteratorError = undefined;
                _context3.prev = 6;

                for (_iterator = (0, _getIterator3.default)(res); !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
                  item = _step.value;

                  if (item.wechatUserId === wechatUser.wechatUserId) {
                    this.activityParticipationId = item.id;
                  }
                }
                _context3.next = 14;
                break;

              case 10:
                _context3.prev = 10;
                _context3.t0 = _context3['catch'](6);
                _didIteratorError = true;
                _iteratorError = _context3.t0;

              case 14:
                _context3.prev = 14;
                _context3.prev = 15;

                if (!_iteratorNormalCompletion && _iterator.return) {
                  _iterator.return();
                }

              case 17:
                _context3.prev = 17;

                if (!_didIteratorError) {
                  _context3.next = 20;
                  break;
                }

                throw _iteratorError;

              case 20:
                return _context3.finish(17);

              case 21:
                return _context3.finish(14);

              case 22:
              case 'end':
                return _context3.stop();
            }
          }
        }, _callee3, this, [[6, 10, 14, 22], [15,, 17, 21]]);
      }));

      function getParticipationsByActivityId() {
        return _ref3.apply(this, arguments);
      }

      return getParticipationsByActivityId;
    }(),
    /**
     * 
     */
    submit: function submit() {
      this.$root.$children[0].switchLoading(true, '上传中');
      if (this.$refs.upload.files.length) {
        this.$refs.upload.$refs.startUpload.click();
      } else {
        var upload = {
          activityParticipationId: this.activityParticipationId,
          signNote: this.signNote,
          title: this.activity.title,
          wechatUserId: wechatUser.wechatUserId
        };
        this.createClockIn(upload);
      }
    },

    /**
     * 
     */
    finishUpload: function finishUpload() {
      console.log(this.$refs.upload.ossPath);
      var images = [];
      var _iteratorNormalCompletion2 = true;
      var _didIteratorError2 = false;
      var _iteratorError2 = undefined;

      try {
        for (var _iterator2 = (0, _getIterator3.default)(this.$refs.upload.ossPath), _step2; !(_iteratorNormalCompletion2 = (_step2 = _iterator2.next()).done); _iteratorNormalCompletion2 = true) {
          var item = _step2.value;

          images.push({
            ossPath: item
          });
        }
      } catch (err) {
        _didIteratorError2 = true;
        _iteratorError2 = err;
      } finally {
        try {
          if (!_iteratorNormalCompletion2 && _iterator2.return) {
            _iterator2.return();
          }
        } finally {
          if (_didIteratorError2) {
            throw _iteratorError2;
          }
        }
      }

      var upload = {
        activityParticipationId: this.activityParticipationId,
        signNote: this.signNote,
        pics: images,
        title: this.activity.title,
        wechatUserId: wechatUser.wechatUserId
      };
      this.createClockIn(upload);
    },

    /**
     * 
     */
    createClockIn: function () {
      var _ref4 = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee4(params) {
        var res;
        return _regenerator2.default.wrap(function _callee4$(_context4) {
          while (1) {
            switch (_context4.prev = _context4.next) {
              case 0:
                _context4.next = 2;
                return (0, _createClockIn3.default)(params);

              case 2:
                res = _context4.sent;

                this.$root.$children[0].switchLoading(false);
                if (res.status === 400) {
                  (0, _utils.showMessage)('error', res.errorKey);
                } else {
                  (0, _utils.showMessage)('success', '打卡成功');
                  this.$router.push({ path: '/detail/' + this.activityId });
                }
                this.getUser();

              case 6:
              case 'end':
                return _context4.stop();
            }
          }
        }, _callee4, this);
      }));

      function createClockIn(_x) {
        return _ref4.apply(this, arguments);
      }

      return createClockIn;
    }()
  },
  mounted: function mounted() {
    this.activityId = this.$route.params.id;
    this.getUser();
  }
}; //
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

/***/ }),
/* 129 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _regenerator = __webpack_require__(0);

var _regenerator2 = _interopRequireDefault(_regenerator);

var _asyncToGenerator2 = __webpack_require__(1);

var _asyncToGenerator3 = _interopRequireDefault(_asyncToGenerator2);

var _iview = __webpack_require__(7);

var _getClockinsByWechatUserIdAndDate2 = __webpack_require__(347);

var _getClockinsByWechatUserIdAndDate3 = _interopRequireDefault(_getClockinsByWechatUserIdAndDate2);

var _getActivityParticipation = __webpack_require__(121);

var _getActivityParticipation2 = _interopRequireDefault(_getActivityParticipation);

var _getActivitiesByWechatUserId2 = __webpack_require__(119);

var _getActivitiesByWechatUserId3 = _interopRequireDefault(_getActivitiesByWechatUserId2);

var _utils = __webpack_require__(2);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.default = {
  name: "personalCenter",
  components: {
    DatePicker: _iview.DatePicker,
    Icon: _iview.Icon
  },
  data: function data() {
    return {
      userInfo: wechatUser,
      lists: [],
      chooseDate: (0, _utils.formatDate)(new Date()),
      isOpen: true,
      isMyPublish: 0,
      record: [],
      activityStatus: {
        1: "进行中",
        2: "已结束"
      }
    };
  },

  methods: {
    /**
     * 
     */
    init: function () {
      var _ref = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee() {
        return _regenerator2.default.wrap(function _callee$(_context) {
          while (1) {
            switch (_context.prev = _context.next) {
              case 0:
                _context.next = 2;
                return this.getClockinsByWechatUserIdAndDate();

              case 2:
                _context.next = 4;
                return this.getActivitiesByWechatUserId();

              case 4:
              case 'end':
                return _context.stop();
            }
          }
        }, _callee, this);
      }));

      function init() {
        return _ref.apply(this, arguments);
      }

      return init;
    }(),
    /**
     * 
     */
    handleDateChange: function handleDateChange(date) {
      this.chooseDate = date;
      this.getClockinsByWechatUserIdAndDate();
    },

    /**
     * 
     */
    getClockinsByWechatUserIdAndDate: function () {
      var _ref2 = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee2() {
        var res;
        return _regenerator2.default.wrap(function _callee2$(_context2) {
          while (1) {
            switch (_context2.prev = _context2.next) {
              case 0:
                this.$root.$children[0].switchLoading(true, '加载中');
                _context2.next = 3;
                return (0, _getClockinsByWechatUserIdAndDate3.default)({
                  wechatUserId: this.userInfo.wechatUserId,
                  yearMonthDate: this.chooseDate
                });

              case 3:
                res = _context2.sent;

                this.$root.$children[0].switchLoading(false);
                this.record = res;

              case 6:
              case 'end':
                return _context2.stop();
            }
          }
        }, _callee2, this);
      }));

      function getClockinsByWechatUserIdAndDate() {
        return _ref2.apply(this, arguments);
      }

      return getClockinsByWechatUserIdAndDate;
    }(),

    getActivitiesByWechatUserId: function () {
      var _ref3 = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee3() {
        var res;
        return _regenerator2.default.wrap(function _callee3$(_context3) {
          while (1) {
            switch (_context3.prev = _context3.next) {
              case 0:
                _context3.next = 2;
                return (0, _getActivitiesByWechatUserId3.default)({
                  wechatUserId: wechatUser.wechatUserId,
                  sort: []
                });

              case 2:
                res = _context3.sent;

                this.lists = res;

              case 4:
              case 'end':
                return _context3.stop();
            }
          }
        }, _callee3, this);
      }));

      function getActivitiesByWechatUserId() {
        return _ref3.apply(this, arguments);
      }

      return getActivitiesByWechatUserId;
    }(),
    /**
     * 
     */
    gotoDetail: function gotoDetail(id) {
      this.$router.push({ path: 'detail/' + id });
    },

    // getActivityParticipation: async function (id) {
    //   let res = await getActivityParticipation(id);
    //   this.$router.push('/detail/' + res.activityId);
    // }
    /**
    * 
    */
    openClickPreview: function openClickPreview(imgList, pos) {
      this.$root.$children[0].openImagePreview('open', imgList, pos);
    },
    showPop: function showPop(index) {
      var _this = this;

      var array = [];
      this.lists.forEach(function (item) {
        item.isShowPop = false;
      });
      this.lists[index].isShowPop = true;
      this.lists = this.lists.concat(array);
      setTimeout(function () {
        _this.lists.forEach(function (item) {
          item.isShowPop = false;
        });
        _this.lists = _this.lists.concat(array);
      }, 1000);
    }
  },
  mounted: function mounted() {
    this.init();
  }
}; //
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

/***/ }),
/* 130 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _getIterator2 = __webpack_require__(13);

var _getIterator3 = _interopRequireDefault(_getIterator2);

var _regenerator = __webpack_require__(0);

var _regenerator2 = _interopRequireDefault(_regenerator);

var _asyncToGenerator2 = __webpack_require__(1);

var _asyncToGenerator3 = _interopRequireDefault(_asyncToGenerator2);

var _iview = __webpack_require__(7);

var _utils = __webpack_require__(2);

var _getClockinsByActivityParticipationId2 = __webpack_require__(354);

var _getClockinsByActivityParticipationId3 = _interopRequireDefault(_getClockinsByActivityParticipationId2);

var _getParticipationsByActivityId2 = __webpack_require__(68);

var _getParticipationsByActivityId3 = _interopRequireDefault(_getParticipationsByActivityId2);

var _getWechatUser2 = __webpack_require__(355);

var _getWechatUser3 = _interopRequireDefault(_getWechatUser2);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.default = {
  name: "record",
  components: {
    Row: _iview.Row,
    Col: _iview.Col,
    Icon: _iview.Icon,
    Timeline: _iview.Timeline,
    TimelineItem: _iview.TimelineItem
  },
  filters: {
    decodeBase64: function decodeBase64(val) {
      return val ? (0, _utils.decodeBase64)(val) : val;
    }
  },
  data: function data() {
    return {
      activityParticipationId: '',
      wechatUserId: this.$route.query.wechatUserId,
      activityId: this.$route.query.activity,
      record: [],
      rate: parseInt(this.$route.query.rate) + 1,
      userInfo: {}
    };
  },

  methods: {
    /**
     * 
     */
    load: function () {
      var _ref = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee() {
        return _regenerator2.default.wrap(function _callee$(_context) {
          while (1) {
            switch (_context.prev = _context.next) {
              case 0:
                this.$root.$children[0].switchLoading(true, '活动加载中');
                _context.next = 3;
                return this.getWechatUser();

              case 3:
                _context.next = 5;
                return this.getParticipationsByActivityId();

              case 5:
                this.$root.$children[0].switchLoading(false);

              case 6:
              case 'end':
                return _context.stop();
            }
          }
        }, _callee, this);
      }));

      function load() {
        return _ref.apply(this, arguments);
      }

      return load;
    }(),
    /**
     * 
     */
    getParticipationsByActivityId: function () {
      var _ref2 = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee2() {
        var res, _iteratorNormalCompletion, _didIteratorError, _iteratorError, _iterator, _step, item;

        return _regenerator2.default.wrap(function _callee2$(_context2) {
          while (1) {
            switch (_context2.prev = _context2.next) {
              case 0:
                _context2.next = 2;
                return (0, _getParticipationsByActivityId3.default)({ activityId: this.activityId });

              case 2:
                res = _context2.sent;
                _iteratorNormalCompletion = true;
                _didIteratorError = false;
                _iteratorError = undefined;
                _context2.prev = 6;

                for (_iterator = (0, _getIterator3.default)(res); !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
                  item = _step.value;

                  if (item.wechatUserId === this.wechatUserId) {
                    this.activityParticipationId = item.id;
                  }
                }
                _context2.next = 14;
                break;

              case 10:
                _context2.prev = 10;
                _context2.t0 = _context2['catch'](6);
                _didIteratorError = true;
                _iteratorError = _context2.t0;

              case 14:
                _context2.prev = 14;
                _context2.prev = 15;

                if (!_iteratorNormalCompletion && _iterator.return) {
                  _iterator.return();
                }

              case 17:
                _context2.prev = 17;

                if (!_didIteratorError) {
                  _context2.next = 20;
                  break;
                }

                throw _iteratorError;

              case 20:
                return _context2.finish(17);

              case 21:
                return _context2.finish(14);

              case 22:
                this.getClockinsByActivityParticipationId();

              case 23:
              case 'end':
                return _context2.stop();
            }
          }
        }, _callee2, this, [[6, 10, 14, 22], [15,, 17, 21]]);
      }));

      function getParticipationsByActivityId() {
        return _ref2.apply(this, arguments);
      }

      return getParticipationsByActivityId;
    }(),
    /**
     * 
     */
    getClockinsByActivityParticipationId: function () {
      var _ref3 = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee3() {
        var res;
        return _regenerator2.default.wrap(function _callee3$(_context3) {
          while (1) {
            switch (_context3.prev = _context3.next) {
              case 0:
                _context3.next = 2;
                return (0, _getClockinsByActivityParticipationId3.default)({
                  activityParticipationId: this.activityParticipationId
                });

              case 2:
                res = _context3.sent;

                this.record = res;

              case 4:
              case 'end':
                return _context3.stop();
            }
          }
        }, _callee3, this);
      }));

      function getClockinsByActivityParticipationId() {
        return _ref3.apply(this, arguments);
      }

      return getClockinsByActivityParticipationId;
    }(),
    /**
     * 
     */
    getWechatUser: function () {
      var _ref4 = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee4() {
        var res;
        return _regenerator2.default.wrap(function _callee4$(_context4) {
          while (1) {
            switch (_context4.prev = _context4.next) {
              case 0:
                _context4.next = 2;
                return (0, _getWechatUser3.default)(this.wechatUserId);

              case 2:
                res = _context4.sent;

                this.userInfo = res;

              case 4:
              case 'end':
                return _context4.stop();
            }
          }
        }, _callee4, this);
      }));

      function getWechatUser() {
        return _ref4.apply(this, arguments);
      }

      return getWechatUser;
    }(),
    /**
     * 
     */
    onlyFormatDate: function onlyFormatDate(date) {
      return (0, _utils.onlyFormatDate)(date);
    },

    /**
    * 
    */
    openClickPreview: function openClickPreview(imgList, pos) {
      this.$root.$children[0].openImagePreview('open', imgList, pos);
    }
  },
  mounted: function mounted() {
    this.load();
  }
}; //
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//

/***/ }),
/* 131 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _regenerator = __webpack_require__(0);

var _regenerator2 = _interopRequireDefault(_regenerator);

var _asyncToGenerator2 = __webpack_require__(1);

var _asyncToGenerator3 = _interopRequireDefault(_asyncToGenerator2);

var _popularActivity = __webpack_require__(116);

var _popularActivity2 = _interopRequireDefault(_popularActivity);

var _getAllFitnessActivities2 = __webpack_require__(118);

var _getAllFitnessActivities3 = _interopRequireDefault(_getAllFitnessActivities2);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

//
//
//
//

exports.default = {
  name: "popluar",
  components: {
    popularActivity: _popularActivity2.default
  },
  data: function data() {
    return {
      activities: []
    };
  },

  methods: {
    load: function () {
      var _ref = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee() {
        return _regenerator2.default.wrap(function _callee$(_context) {
          while (1) {
            switch (_context.prev = _context.next) {
              case 0:
                this.getAllFitnessActivities();

              case 1:
              case 'end':
                return _context.stop();
            }
          }
        }, _callee, this);
      }));

      function load() {
        return _ref.apply(this, arguments);
      }

      return load;
    }(),
    getAllFitnessActivities: function () {
      var _ref2 = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee2() {
        var res;
        return _regenerator2.default.wrap(function _callee2$(_context2) {
          while (1) {
            switch (_context2.prev = _context2.next) {
              case 0:
                this.$root.$children[0].switchLoading(true, '活动查询中');
                _context2.next = 3;
                return (0, _getAllFitnessActivities3.default)({
                  page: 0,
                  size: 100,
                  sort: ['modifyTime', 'desc']
                });

              case 3:
                res = _context2.sent;

                this.activities = res;
                this.$root.$children[0].switchLoading(false);

              case 6:
              case 'end':
                return _context2.stop();
            }
          }
        }, _callee2, this);
      }));

      function getAllFitnessActivities() {
        return _ref2.apply(this, arguments);
      }

      return getAllFitnessActivities;
    }()
  },
  mounted: function mounted() {
    this.load();
  }
};

/***/ }),
/* 132 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _getIterator2 = __webpack_require__(13);

var _getIterator3 = _interopRequireDefault(_getIterator2);

var _regenerator = __webpack_require__(0);

var _regenerator2 = _interopRequireDefault(_regenerator);

var _asyncToGenerator2 = __webpack_require__(1);

var _asyncToGenerator3 = _interopRequireDefault(_asyncToGenerator2);

var _iview = __webpack_require__(7);

var _utils = __webpack_require__(2);

var _createComments2 = __webpack_require__(133);

var _createComments3 = _interopRequireDefault(_createComments2);

var _upload = __webpack_require__(14);

var _upload2 = _interopRequireDefault(_upload);

var _modal = __webpack_require__(26);

var _modal2 = _interopRequireDefault(_modal);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.default = {
  name: 'discuss',
  components: {
    Icon: _iview.Icon,
    Upload: _upload2.default,
    Modal: _modal2.default
  },
  data: function data() {
    return {
      content: '',
      dialog: '',
      id: ''
    };
  },

  methods: {
    /**
     * 
     */
    createComments: function () {
      var _ref = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee(params) {
        var _this = this;

        var res;
        return _regenerator2.default.wrap(function _callee$(_context) {
          while (1) {
            switch (_context.prev = _context.next) {
              case 0:
                this.$root.$children[0].switchLoading(true, '讨论发表中');
                _context.next = 3;
                return (0, _createComments3.default)(params);

              case 3:
                res = _context.sent;

                this.$root.$children[0].switchLoading(false);

                if (res) {
                  _context.next = 7;
                  break;
                }

                return _context.abrupt('return');

              case 7:

                (0, _utils.showMessage)('success', '发布成功', function () {
                  _this.$router.replace('/detail/' + res.objectId);
                });

              case 8:
              case 'end':
                return _context.stop();
            }
          }
        }, _callee, this);
      }));

      function createComments(_x) {
        return _ref.apply(this, arguments);
      }

      return createComments;
    }(),
    /**
     * 
     */
    submit: function submit() {
      if (!this.content) {
        this.dialog = "请输入讨论内容";
        this.$refs.modal.isOpen = true;
        return;
      }

      if (this.$refs.upload.files.length) {
        this.$refs.upload.$refs.startUpload.click();
      } else {
        this.finishUpload();
      }
    },

    /**
     * 
     */
    finishUpload: function finishUpload() {
      console.log(this.$refs.upload.ossPath);
      var commentPics = [];
      var _iteratorNormalCompletion = true;
      var _didIteratorError = false;
      var _iteratorError = undefined;

      try {
        for (var _iterator = (0, _getIterator3.default)(this.$refs.upload.ossPath), _step; !(_iteratorNormalCompletion = (_step = _iterator.next()).done); _iteratorNormalCompletion = true) {
          var item = _step.value;

          commentPics.push({
            ossPath: item
          });
        }
      } catch (err) {
        _didIteratorError = true;
        _iteratorError = err;
      } finally {
        try {
          if (!_iteratorNormalCompletion && _iterator.return) {
            _iterator.return();
          }
        } finally {
          if (_didIteratorError) {
            throw _iteratorError;
          }
        }
      }

      this.createComments({
        commentPics: commentPics,
        channel: 'FIT',
        content: this.content,
        // createTime: formatDateString(new Date()),
        objectId: this.id,
        wechatUserId: wechatUser.wechatUserId
      });
    }
  },
  mounted: function mounted() {
    this.id = this.$route.params.id;
  }
}; //
//
//
//
//
//
//
//
//
//
//
//
//

/***/ }),
/* 133 */,
/* 134 */,
/* 135 */,
/* 136 */,
/* 137 */,
/* 138 */,
/* 139 */,
/* 140 */,
/* 141 */,
/* 142 */,
/* 143 */,
/* 144 */,
/* 145 */,
/* 146 */,
/* 147 */,
/* 148 */,
/* 149 */,
/* 150 */,
/* 151 */,
/* 152 */,
/* 153 */,
/* 154 */,
/* 155 */,
/* 156 */,
/* 157 */,
/* 158 */,
/* 159 */,
/* 160 */,
/* 161 */,
/* 162 */,
/* 163 */,
/* 164 */,
/* 165 */,
/* 166 */,
/* 167 */,
/* 168 */,
/* 169 */,
/* 170 */,
/* 171 */,
/* 172 */,
/* 173 */,
/* 174 */,
/* 175 */,
/* 176 */,
/* 177 */,
/* 178 */,
/* 179 */,
/* 180 */,
/* 181 */,
/* 182 */,
/* 183 */,
/* 184 */,
/* 185 */,
/* 186 */,
/* 187 */,
/* 188 */,
/* 189 */,
/* 190 */,
/* 191 */,
/* 192 */,
/* 193 */,
/* 194 */,
/* 195 */,
/* 196 */,
/* 197 */,
/* 198 */,
/* 199 */,
/* 200 */,
/* 201 */,
/* 202 */,
/* 203 */,
/* 204 */,
/* 205 */,
/* 206 */,
/* 207 */,
/* 208 */,
/* 209 */,
/* 210 */,
/* 211 */,
/* 212 */,
/* 213 */,
/* 214 */,
/* 215 */,
/* 216 */,
/* 217 */,
/* 218 */,
/* 219 */,
/* 220 */,
/* 221 */,
/* 222 */,
/* 223 */,
/* 224 */,
/* 225 */,
/* 226 */,
/* 227 */,
/* 228 */,
/* 229 */,
/* 230 */,
/* 231 */,
/* 232 */,
/* 233 */,
/* 234 */,
/* 235 */,
/* 236 */,
/* 237 */,
/* 238 */,
/* 239 */,
/* 240 */,
/* 241 */,
/* 242 */,
/* 243 */,
/* 244 */,
/* 245 */,
/* 246 */,
/* 247 */,
/* 248 */,
/* 249 */,
/* 250 */,
/* 251 */,
/* 252 */,
/* 253 */,
/* 254 */,
/* 255 */,
/* 256 */,
/* 257 */,
/* 258 */,
/* 259 */,
/* 260 */,
/* 261 */,
/* 262 */,
/* 263 */,
/* 264 */,
/* 265 */,
/* 266 */,
/* 267 */,
/* 268 */,
/* 269 */,
/* 270 */,
/* 271 */,
/* 272 */,
/* 273 */,
/* 274 */,
/* 275 */,
/* 276 */,
/* 277 */,
/* 278 */,
/* 279 */,
/* 280 */,
/* 281 */,
/* 282 */,
/* 283 */,
/* 284 */,
/* 285 */,
/* 286 */,
/* 287 */,
/* 288 */,
/* 289 */,
/* 290 */,
/* 291 */,
/* 292 */,
/* 293 */,
/* 294 */,
/* 295 */,
/* 296 */,
/* 297 */,
/* 298 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


var _vue = __webpack_require__(20);

var _vue2 = _interopRequireDefault(_vue);

var _App = __webpack_require__(299);

var _App2 = _interopRequireDefault(_App);

var _index = __webpack_require__(306);

var _index2 = _interopRequireDefault(_index);

__webpack_require__(64);

__webpack_require__(65);

__webpack_require__(66);

var _vTooltip = __webpack_require__(134);

var _vTooltip2 = _interopRequireDefault(_vTooltip);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

_vue2.default.use(_vTooltip2.default);
// import iView from 'iview'
// Vue.use(iView)
// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
new _vue2.default({
  el: '#app',
  router: _index2.default,
  template: '<App/>',
  components: { App: _App2.default }
});

/***/ }),
/* 299 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_App_vue__ = __webpack_require__(112);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_App_vue___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_App_vue__);
/* harmony namespace reexport (unknown) */ for(var __WEBPACK_IMPORT_KEY__ in __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_App_vue__) if(__WEBPACK_IMPORT_KEY__ !== 'default') (function(key) { __webpack_require__.d(__webpack_exports__, key, function() { return __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_App_vue__[key]; }) }(__WEBPACK_IMPORT_KEY__));
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_vue_loader_lib_template_compiler_index_id_data_v_16d9b60e_hasScoped_false_optionsId_0_buble_transforms_node_modules_vue_loader_lib_selector_type_template_index_0_App_vue__ = __webpack_require__(305);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__node_modules_vue_loader_lib_runtime_component_normalizer__ = __webpack_require__(6);
var disposed = false
function injectStyle (context) {
  if (disposed) return
  __webpack_require__(300)
}
/* script */


/* template */

/* template functional */
var __vue_template_functional__ = false
/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = null
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null

var Component = Object(__WEBPACK_IMPORTED_MODULE_2__node_modules_vue_loader_lib_runtime_component_normalizer__["a" /* default */])(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_App_vue___default.a,
  __WEBPACK_IMPORTED_MODULE_1__node_modules_vue_loader_lib_template_compiler_index_id_data_v_16d9b60e_hasScoped_false_optionsId_0_buble_transforms_node_modules_vue_loader_lib_selector_type_template_index_0_App_vue__["a" /* render */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_vue_loader_lib_template_compiler_index_id_data_v_16d9b60e_hasScoped_false_optionsId_0_buble_transforms_node_modules_vue_loader_lib_selector_type_template_index_0_App_vue__["b" /* staticRenderFns */],
  __vue_template_functional__,
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "dev\\pages\\fit\\App.vue"

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-16d9b60e", Component.options)
  } else {
    hotAPI.reload("data-v-16d9b60e", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["default"] = (Component.exports);


/***/ }),
/* 300 */
/***/ (function(module, exports, __webpack_require__) {

// style-loader: Adds some css to the DOM by adding a <style> tag

// load the styles
var content = __webpack_require__(301);
if(typeof content === 'string') content = [[module.i, content, '']];
if(content.locals) module.exports = content.locals;
// add the styles to the DOM
var add = __webpack_require__(16).default
var update = add("17bcf6d8", content, false, {});
// Hot Module Replacement
if(false) {
 // When the styles change, update the <style> tags
 if(!content.locals) {
   module.hot.accept("!!../../../node_modules/css-loader/index.js!../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"optionsId\":\"0\",\"vue\":true,\"scoped\":false,\"sourceMap\":false}!../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./App.vue", function() {
     var newContent = require("!!../../../node_modules/css-loader/index.js!../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"optionsId\":\"0\",\"vue\":true,\"scoped\":false,\"sourceMap\":false}!../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./App.vue");
     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
     update(newContent);
   });
 }
 // When the module is disposed, remove the <style> tags
 module.hot.dispose(function() { update(); });
}

/***/ }),
/* 301 */
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(3)(false);
// imports


// module
exports.push([module.i, "\n.app {\n  padding-bottom: 0.5rem\n}\n", ""]);

// exports


/***/ }),
/* 302 */,
/* 303 */,
/* 304 */,
/* 305 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return render; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "b", function() { return staticRenderFns; });
var render = function() {
  var _vm = this
  var _h = _vm.$createElement
  var _c = _vm._self._c || _h
  return _c(
    "div",
    { staticClass: "app" },
    [
      _c("keep-alive", { attrs: { include: "index" } }, [_c("router-view")], 1),
      _vm._v(" "),
      _c("foot", { attrs: { list: _vm.list } }),
      _vm._v(" "),
      _c("spin", {
        attrs: { isLoading: _vm.isLoading, spinText: _vm.spinText }
      }),
      _vm._v(" "),
      _c("image-preview", {
        directives: [
          {
            name: "show",
            rawName: "v-show",
            value: _vm.isImagePreview,
            expression: "isImagePreview"
          }
        ],
        attrs: { image: _vm.imageList, pos: _vm.imagePreviewPos }
      })
    ],
    1
  )
}
var staticRenderFns = []
render._withStripped = true

if (false) {
  module.hot.accept()
  if (module.hot.data) {
    require("vue-hot-reload-api")      .rerender("data-v-16d9b60e", { render: render, staticRenderFns: staticRenderFns })
  }
}

/***/ }),
/* 306 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _regenerator = __webpack_require__(0);

var _regenerator2 = _interopRequireDefault(_regenerator);

var _stringify = __webpack_require__(34);

var _stringify2 = _interopRequireDefault(_stringify);

var _asyncToGenerator2 = __webpack_require__(1);

var _asyncToGenerator3 = _interopRequireDefault(_asyncToGenerator2);

var _vue = __webpack_require__(20);

var _vue2 = _interopRequireDefault(_vue);

var _vueRouter = __webpack_require__(53);

var _vueRouter2 = _interopRequireDefault(_vueRouter);

var _utils = __webpack_require__(2);

var _getWechatInfo = __webpack_require__(58);

var _getWechatInfo2 = _interopRequireDefault(_getWechatInfo);

var _index = __webpack_require__(307);

var _index2 = _interopRequireDefault(_index);

var _detail = __webpack_require__(316);

var _detail2 = _interopRequireDefault(_detail);

var _publish = __webpack_require__(327);

var _publish2 = _interopRequireDefault(_publish);

var _PublishModify = __webpack_require__(332);

var _PublishModify2 = _interopRequireDefault(_PublishModify);

var _check = __webpack_require__(337);

var _check2 = _interopRequireDefault(_check);

var _userCenter = __webpack_require__(342);

var _userCenter2 = _interopRequireDefault(_userCenter);

var _record = __webpack_require__(349);

var _record2 = _interopRequireDefault(_record);

var _popular = __webpack_require__(357);

var _popular2 = _interopRequireDefault(_popular);

var _discuss = __webpack_require__(361);

var _discuss2 = _interopRequireDefault(_discuss);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

_vue2.default.use(_vueRouter2.default);

var staticRouter = [{
  path: '/',
  name: 'Index',
  component: _index2.default
}, {
  path: '/detail/:id',
  name: 'Detail',
  component: _detail2.default
}, {
  path: '/publish',
  name: 'Publish',
  component: _publish2.default
}, {
  path: '/publish/:id',
  name: 'PublishModify',
  component: _PublishModify2.default
}, {
  path: '/check/:id',
  name: 'Check',
  component: _check2.default
}, {
  path: '/user',
  name: 'User',
  component: _userCenter2.default
}, {
  path: '/record',
  name: 'Record',
  component: _record2.default
}, {
  path: '/popular',
  name: 'Popular',
  component: _popular2.default
}, {
  path: '/discuss/:id',
  name: 'Discuss',
  component: _discuss2.default
}];

var router = new _vueRouter2.default({
  mode: 'hash',
  routes: staticRouter,
  scrollBehavior: function scrollBehavior(to, from, savedPosition) {
    return { x: 0, y: 0 };
  }
});

router.beforeEach(function () {
  var _ref = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee(to, from, next) {
    var _wechatUser, code, res;

    return _regenerator2.default.wrap(function _callee$(_context) {
      while (1) {
        switch (_context.prev = _context.next) {
          case 0:
            _wechatUser = JSON.parse(sessionStorage.getItem('wechatUser'));
            // let _wechatUser = JSON.parse('{"openId":"oOscRuFJPdiadffOTdOG45Foij9I","userId":"10","userName":"徐伟鸣","sex":"1","province":"上海","city":"虹口","country":"中国","headimgurl":"http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83ep5ib0e34QJfiaHXdV9eZphpaMcRcvFR1e4Xppicxj7Ppyq2F33mNnYDib6zoVSXDbQ36YhiaQB3QWNMjA/132","privilege":"[]","unionid":null}')

            if (!_wechatUser) {
              _context.next = 6;
              break;
            }

            window.wechatUser = {
              wechatUserId: _wechatUser.userId,
              avatar: _wechatUser.headimgurl,
              nickName: _wechatUser.userName,
              sex: _wechatUser.sex,
              country: _wechatUser.country,
              province: _wechatUser.province,
              city: _wechatUser.city
            };
            next();
            _context.next = 17;
            break;

          case 6:
            code = (0, _utils.getUrlParam)('code');

            if (!code) {
              _context.next = 16;
              break;
            }

            _context.next = 10;
            return (0, _getWechatInfo2.default)(code);

          case 10:
            res = _context.sent;

            if (res) sessionStorage.setItem('wechatUser', (0, _stringify2.default)(res));
            window.wechatUser = {
              wechatUserId: res.userId,
              avatar: res.headimgurl,
              nickName: res.userName,
              sex: res.sex,
              country: res.country,
              province: res.province,
              city: res.city
            };
            next();
            _context.next = 17;
            break;

          case 16:
            location.href = (0, _utils.createWechatUrl)();

          case 17:
          case 'end':
            return _context.stop();
        }
      }
    }, _callee, undefined);
  }));

  return function (_x, _x2, _x3) {
    return _ref.apply(this, arguments);
  };
}());

exports.default = router;

/***/ }),
/* 307 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue__ = __webpack_require__(115);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue__);
/* harmony namespace reexport (unknown) */ for(var __WEBPACK_IMPORT_KEY__ in __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue__) if(__WEBPACK_IMPORT_KEY__ !== 'default') (function(key) { __webpack_require__.d(__webpack_exports__, key, function() { return __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue__[key]; }) }(__WEBPACK_IMPORT_KEY__));
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_vue_loader_lib_template_compiler_index_id_data_v_6947c15c_hasScoped_true_optionsId_0_buble_transforms_node_modules_vue_loader_lib_selector_type_template_index_0_index_vue__ = __webpack_require__(315);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__node_modules_vue_loader_lib_runtime_component_normalizer__ = __webpack_require__(6);
var disposed = false
function injectStyle (context) {
  if (disposed) return
  __webpack_require__(308)
}
/* script */


/* template */

/* template functional */
var __vue_template_functional__ = false
/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-6947c15c"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null

var Component = Object(__WEBPACK_IMPORTED_MODULE_2__node_modules_vue_loader_lib_runtime_component_normalizer__["a" /* default */])(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue___default.a,
  __WEBPACK_IMPORTED_MODULE_1__node_modules_vue_loader_lib_template_compiler_index_id_data_v_6947c15c_hasScoped_true_optionsId_0_buble_transforms_node_modules_vue_loader_lib_selector_type_template_index_0_index_vue__["a" /* render */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_vue_loader_lib_template_compiler_index_id_data_v_6947c15c_hasScoped_true_optionsId_0_buble_transforms_node_modules_vue_loader_lib_selector_type_template_index_0_index_vue__["b" /* staticRenderFns */],
  __vue_template_functional__,
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "dev\\pages\\fit\\views\\index\\index.vue"

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-6947c15c", Component.options)
  } else {
    hotAPI.reload("data-v-6947c15c", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["default"] = (Component.exports);


/***/ }),
/* 308 */
/***/ (function(module, exports, __webpack_require__) {


var content = __webpack_require__(309);

if(typeof content === 'string') content = [[module.i, content, '']];

var transform;
var insertInto;



var options = {"hmr":true}

options.transform = transform
options.insertInto = undefined;

var update = __webpack_require__(5)(content, options);

if(content.locals) module.exports = content.locals;

if(false) {
	module.hot.accept("!!../../../../../node_modules/css-loader/index.js!../../../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"optionsId\":\"0\",\"vue\":true,\"id\":\"data-v-6947c15c\",\"scoped\":true,\"sourceMap\":false}!../../../../../node_modules/postcss-loader/lib/index.js!../../../../../node_modules/less-loader/dist/cjs.js!../../../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./index.vue", function() {
		var newContent = require("!!../../../../../node_modules/css-loader/index.js!../../../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"optionsId\":\"0\",\"vue\":true,\"id\":\"data-v-6947c15c\",\"scoped\":true,\"sourceMap\":false}!../../../../../node_modules/postcss-loader/lib/index.js!../../../../../node_modules/less-loader/dist/cjs.js!../../../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./index.vue");

		if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];

		var locals = (function(a, b) {
			var key, idx = 0;

			for(key in a) {
				if(!b || a[key] !== b[key]) return false;
				idx++;
			}

			for(key in b) idx--;

			return idx === 0;
		}(content.locals, newContent.locals));

		if(!locals) throw new Error('Aborting CSS HMR due to changed css-modules locals.');

		update(newContent);
	});

	module.hot.dispose(function() { update(); });
}

/***/ }),
/* 309 */
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(3)(false);
// imports


// module
exports.push([module.i, "\n.fit[data-v-6947c15c] {\n  overflow: auto;\n}\n.top-container[data-v-6947c15c] {\n  width: 100%;\n  height: 1.5rem;\n  background: #FFD52F;\n  text-align: center;\n  padding-top: 0.4rem;\n}\n.avatar-container[data-v-6947c15c] {\n  width: 0.6rem;\n  height: 0.6rem;\n  margin: auto;\n}\n.avatar[data-v-6947c15c] {\n  width: 0.6rem;\n  height: 0.6rem;\n  border-radius: 0.6rem;\n}\n.name[data-v-6947c15c] {\n  font-size: 20px;\n  padding-top: 0.05rem;\n}\n.sport-container[data-v-6947c15c] {\n  width: 100%;\n  height: 1.97rem;\n  background: white;\n  text-align: center;\n}\n.sport-item[data-v-6947c15c] {\n  width: 1rem;\n  padding-top: 0.22rem;\n  margin: auto;\n}\n.sport-item .sport-title[data-v-6947c15c] {\n  color: #A0A0A0;\n}\n.sport-item .sport-num[data-v-6947c15c] {\n  font-size: 58px;\n  color: #FFD52F;\n  line-height: 60px;\n}\n.sport-item .num[data-v-6947c15c] {\n  font-weight: bold;\n}\n.sport-item .sport-day[data-v-6947c15c] {\n  color: #666666;\n}\n.switch-container[data-v-6947c15c] {\n  color: #666666;\n  padding-top: 0.15rem;\n}\n.switch-container .switch-item[data-v-6947c15c] {\n  border-right: 1px solid #A0A0A0;\n}\n.switch-container .switch-item[data-v-6947c15c]:last-child {\n  border: 0;\n}\n.switch-container .switch-icon[data-v-6947c15c] {\n  font-size: 26px;\n}\n.hot-container[data-v-6947c15c] {\n  width: 100%;\n  background: #fff;\n  padding: 0 0.15rem;\n}\n.hot-container .title[data-v-6947c15c] {\n  padding-top: 15px;\n}\n.swiper-container[data-v-6947c15c] {\n  width: 100%;\n  padding-top: 0.2rem;\n}\n.hot-item[data-v-6947c15c] {\n  /*margin-right: 0.2rem;*/\n  margin-bottom: 0.5rem;\n}\n.hot-item .hot-pic-container[data-v-6947c15c] {\n  width: 100%;\n  min-height: 195px;\n  height: auto;\n}\n.hot-item .hot-pic[data-v-6947c15c] {\n  width: 92vw;\n  height: 52vw;\n  background-repeat: no-repeat;\n  background-size: contain;\n  background-position: center center;\n  background-color: #fff;\n}\n.hot-item .hot-title[data-v-6947c15c] {\n  font-size: 14px;\n  color: #000000;\n}\n.hot-item .info[data-v-6947c15c] {\n  display: -moz-box;\n  display: flex;\n  -moz-box-pack: justify;\n       justify-content: space-between;\n  font-size: 12px;\n  color: #A0A0A0;\n}\n.hot-item .description[data-v-6947c15c] {\n  width: 85%;\n  white-space: nowrap;\n  text-overflow: ellipsis;\n  overflow: hidden;\n  color: #A0A0A0;\n}\n.hot-item .num[data-v-6947c15c] {\n  color: black;\n  font-weight: bold;\n}\n.mine-container[data-v-6947c15c] {\n  width: 100%;\n  background-color: #fff;\n  /*padding: 0 0.2rem;*/\n  margin-bottom: 0.5rem;\n}\n.mine-container .title[data-v-6947c15c] {\n  padding-top: 15px;\n  padding-left: 0.2rem;\n}\n.hot-container[data-v-6947c15c],\n.mine-container[data-v-6947c15c] {\n  border-top: 10px solid #f6f6f6;\n}\n", ""]);

// exports


/***/ }),
/* 310 */
/***/ (function(module, exports, __webpack_require__) {


var content = __webpack_require__(311);

if(typeof content === 'string') content = [[module.i, content, '']];

var transform;
var insertInto;



var options = {"hmr":true}

options.transform = transform
options.insertInto = undefined;

var update = __webpack_require__(5)(content, options);

if(content.locals) module.exports = content.locals;

if(false) {
	module.hot.accept("!!../../../node_modules/css-loader/index.js!../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"optionsId\":\"0\",\"vue\":true,\"id\":\"data-v-00e9152c\",\"scoped\":true,\"sourceMap\":false}!../../../node_modules/postcss-loader/lib/index.js!../../../node_modules/less-loader/dist/cjs.js!../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./popularActivity.vue", function() {
		var newContent = require("!!../../../node_modules/css-loader/index.js!../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"optionsId\":\"0\",\"vue\":true,\"id\":\"data-v-00e9152c\",\"scoped\":true,\"sourceMap\":false}!../../../node_modules/postcss-loader/lib/index.js!../../../node_modules/less-loader/dist/cjs.js!../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./popularActivity.vue");

		if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];

		var locals = (function(a, b) {
			var key, idx = 0;

			for(key in a) {
				if(!b || a[key] !== b[key]) return false;
				idx++;
			}

			for(key in b) idx--;

			return idx === 0;
		}(content.locals, newContent.locals));

		if(!locals) throw new Error('Aborting CSS HMR due to changed css-modules locals.');

		update(newContent);
	});

	module.hot.dispose(function() { update(); });
}

/***/ }),
/* 311 */
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(3)(false);
// imports


// module
exports.push([module.i, "\n.activity[data-v-00e9152c] {\n  margin-bottom: 50px;\n}\n.popular[data-v-00e9152c] {\n  background: #fff;\n  padding: 0.15rem;\n}\n.section[data-v-00e9152c] {\n  border-bottom: 1px solid #ECECEC;\n  padding-bottom: 0.1rem;\n}\n.section h3[data-v-00e9152c] {\n  font-size: 14px;\n  color: #000000;\n  font-weight: 400;\n}\n.section .info[data-v-00e9152c] {\n  display: -moz-box;\n  display: flex;\n  -moz-box-pack: justify;\n       justify-content: space-between;\n  font-size: 12px;\n  color: #A0A0A0;\n}\n.section .description[data-v-00e9152c] {\n  width: 70%;\n  white-space: nowrap;\n  text-overflow: ellipsis;\n  overflow: hidden;\n}\n.act-img[data-v-00e9152c] {\n  width: 100%;\n  height: 185px;\n  overflow: hidden;\n}\n.act-img img[data-v-00e9152c] {\n  width: 100%;\n}\n", ""]);

// exports


/***/ }),
/* 312 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return render; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "b", function() { return staticRenderFns; });
var render = function() {
  var _vm = this
  var _h = _vm.$createElement
  var _c = _vm._self._c || _h
  return _c(
    "div",
    { staticClass: "activity" },
    _vm._l(_vm.activities, function(item, index) {
      return _c(
        "div",
        {
          key: index,
          staticClass: "popular",
          on: {
            click: function($event) {
              _vm.gotoDetail(item.id)
            }
          }
        },
        [
          _c("div", { staticClass: "section" }, [
            _c("div", { staticClass: "act-img mb10" }, [
              _c("img", {
                staticClass: "avatar",
                attrs: {
                  src: item.images.length ? item.images[0].ossPath : "",
                  alt: ""
                }
              })
            ]),
            _vm._v(" "),
            _c("h3", [_vm._v(_vm._s(item.title))]),
            _vm._v(" "),
            _c("div", { staticClass: "info" }, [
              _c("p", { staticClass: "description" }, [
                _vm._v(_vm._s(item.descrption))
              ]),
              _c("span", [
                _c("strong", [_vm._v(_vm._s(item.commentCount))]),
                _vm._v("讨论")
              ]),
              _c("span", [
                _c("strong", [_vm._v(_vm._s(item.attendCount))]),
                _vm._v("报名")
              ])
            ])
          ])
        ]
      )
    })
  )
}
var staticRenderFns = []
render._withStripped = true

if (false) {
  module.hot.accept()
  if (module.hot.data) {
    require("vue-hot-reload-api")      .rerender("data-v-00e9152c", { render: render, staticRenderFns: staticRenderFns })
  }
}

/***/ }),
/* 313 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});
var API_SERVER = {
  development: 'http://newapi.aitpgroup.tech:8080/api',
  qa: 'http://newapi.aitpgroup.tech:8080/api',
  production: 'http://newapi.siyu.im:8080/api'
}["development"];

exports.API_SERVER = API_SERVER;

/***/ }),
/* 314 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _regenerator = __webpack_require__(0);

var _regenerator2 = _interopRequireDefault(_regenerator);

var _assign = __webpack_require__(4);

var _assign2 = _interopRequireDefault(_assign);

var _asyncToGenerator2 = __webpack_require__(1);

var _asyncToGenerator3 = _interopRequireDefault(_asyncToGenerator2);

var _utils = __webpack_require__(2);

var _api = __webpack_require__(8);

var _api2 = _interopRequireDefault(_api);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

//   /**
//    * the response likes below:
//    * [
//   {
//     "id": 0,
//     "lastClockInTime": "2018-06-19T16:24:33.445Z",
//     "serialCount": 0,
//     "totallyCount": 0,
//     "wechatUserId": "string",
//     "weeklyCount": 0
//   }
// ]
//    *
//    *
//    */
exports.default = function () {
  var _ref = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee2() {
    var params = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : {
      wechatUserId: wechatUserId
    };
    return _regenerator2.default.wrap(function _callee2$(_context2) {
      while (1) {
        switch (_context2.prev = _context2.next) {
          case 0:
            return _context2.abrupt('return', (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee() {
              var wechatUserId, _search, url, _params, res;

              return _regenerator2.default.wrap(function _callee$(_context) {
                while (1) {
                  switch (_context.prev = _context.next) {
                    case 0:
                      wechatUserId = params.wechatUserId;

                      if (wechatUserId) {
                        _context.next = 3;
                        break;
                      }

                      return _context.abrupt('return');

                    case 3:
                      _search = (0, _utils.joinParams)(params);
                      url = _api2.default.getClockinSummaryByWechatId.url + '?' + _search;
                      _params = (0, _assign2.default)({}, _api2.default.getClockinSummaryByWechatId, {
                        url: url
                      });
                      _context.next = 8;
                      return (0, _utils.fetchAPI)(_params);

                    case 8:
                      res = _context.sent;


                      console.log('getClockinSummaryByWechatId response: ', res);

                      return _context.abrupt('return', res);

                    case 11:
                    case 'end':
                      return _context.stop();
                  }
                }
              }, _callee, this);
            }))());

          case 1:
          case 'end':
            return _context2.stop();
        }
      }
    }, _callee2, this);
  }));

  function getClockinSummaryByWechatId() {
    return _ref.apply(this, arguments);
  }

  return getClockinSummaryByWechatId;
}();

/***/ }),
/* 315 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return render; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "b", function() { return staticRenderFns; });
var render = function() {
  var _vm = this
  var _h = _vm.$createElement
  var _c = _vm._self._c || _h
  return _c(
    "div",
    {
      directives: [
        {
          name: "show",
          rawName: "v-show",
          value: _vm.isLoaded,
          expression: "isLoaded"
        }
      ],
      staticClass: "fit"
    },
    [
      _c("div", { staticClass: "top-container" }, [
        _c("div", { staticClass: "avatar-container" }, [
          _c("img", {
            staticClass: "avatar",
            attrs: { src: _vm.userInfo.avatar, alt: "" }
          })
        ]),
        _vm._v(" "),
        _c("p", { staticClass: "name black" }, [
          _vm._v(_vm._s(_vm.userInfo.nickName))
        ])
      ]),
      _vm._v(" "),
      _c(
        "div",
        { staticClass: "sport-container" },
        [
          _c("div", { staticClass: "sport-item" }, [
            _c("p", { staticClass: "sport-title" }, [_vm._v("打卡总天数")]),
            _vm._v(" "),
            _c("p", { staticClass: "sport-num" }, [
              _vm._v(_vm._s(_vm.summary.totallyCount))
            ]),
            _vm._v(" "),
            _c("p", { staticClass: "sport-day" }, [
              _vm._v("本周已打卡\n        "),
              _c("span", { staticClass: "num" }, [
                _vm._v(_vm._s(_vm.summary.weeklyCount))
              ]),
              _vm._v("\n        天\n      ")
            ])
          ]),
          _vm._v(" "),
          _c(
            "Row",
            { staticClass: "switch-container" },
            [
              _c(
                "Col",
                { staticClass: "black switch-item", attrs: { span: "12" } },
                [
                  _c(
                    "p",
                    {
                      on: {
                        click: function($event) {
                          _vm.goAnchor("#anchor")
                        }
                      }
                    },
                    [
                      _c("Icon", {
                        staticClass: "switch-icon",
                        attrs: { type: "android-happy" }
                      })
                    ],
                    1
                  ),
                  _vm._v(" "),
                  _c("p", [_vm._v("去打卡")])
                ]
              ),
              _vm._v(" "),
              _c(
                "router-link",
                { attrs: { to: "/user" } },
                [
                  _c(
                    "Col",
                    { staticClass: "black switch-item", attrs: { span: "12" } },
                    [
                      _c(
                        "p",
                        [
                          _c("Icon", {
                            staticClass: "switch-icon",
                            attrs: { type: "calendar" }
                          })
                        ],
                        1
                      ),
                      _vm._v(" "),
                      _c("p", [
                        _vm._v("连续打卡\n            "),
                        _c("span", { staticClass: "num" }, [
                          _vm._v(_vm._s(_vm.summary.serialCount))
                        ]),
                        _vm._v("\n            天")
                      ])
                    ]
                  )
                ],
                1
              )
            ],
            1
          )
        ],
        1
      ),
      _vm._v(" "),
      _c("div", { staticClass: "hot-container" }, [
        _c(
          "div",
          { staticClass: "title" },
          [
            _c(
              "router-link",
              {
                staticStyle: {
                  color: "#495060",
                  display: "block",
                  overflow: "hidden"
                },
                attrs: { to: "/popular" }
              },
              [
                _c("span", { staticClass: "fl fs16" }, [_vm._v("热门活动")]),
                _vm._v(" "),
                _c("span", { staticClass: "fr fs16" }, [
                  _c("span", { staticClass: "fs12" }, [_vm._v("查看所有>")])
                ])
              ]
            )
          ],
          1
        ),
        _vm._v(" "),
        _c("div", { staticClass: "hot-item-container" }, [
          _c("div", { staticClass: "swiper-container" }, [
            _c(
              "div",
              { staticClass: "swiper-wrapper" },
              _vm._l(_vm.activities, function(item, index) {
                return _c(
                  "div",
                  {
                    key: index,
                    staticClass: "swiper-slide hot-item",
                    on: {
                      click: function($event) {
                        _vm.gotoDetail(item.id)
                      }
                    }
                  },
                  [
                    _c("div", { staticClass: "hot-pic-container" }, [
                      _c("div", {
                        staticClass: "hot-pic",
                        style:
                          "background-image: url(" +
                          item.images[0].ossPath +
                          ");"
                      })
                    ]),
                    _vm._v(" "),
                    _c("p", { staticClass: "hot-title" }, [
                      _vm._v(_vm._s(item.title))
                    ]),
                    _vm._v(" "),
                    _c("div", { staticClass: "info" }, [
                      _c("p", { staticClass: "description" }, [
                        _vm._v(_vm._s(item.descrption))
                      ]),
                      _c("span", [
                        _c("span", { staticClass: "num" }, [
                          _vm._v(_vm._s(item.attendCount))
                        ]),
                        _vm._v("报名")
                      ])
                    ])
                  ]
                )
              })
            )
          ])
        ])
      ]),
      _vm._v(" "),
      _c(
        "div",
        { staticClass: "mine-container", attrs: { id: "anchor" } },
        [
          _c("p", { staticClass: "title fs16" }, [_vm._v("我的活动")]),
          _vm._v(" "),
          _vm.myActivities.length
            ? _c("popularActivity", { attrs: { activities: _vm.myActivities } })
            : _vm._e(),
          _vm._v(" "),
          !_vm.myActivities.length
            ? _c("p", { staticClass: "tr m15" }, [
                _vm._v("您暂时没有参加活动哦")
              ])
            : _vm._e()
        ],
        1
      )
    ]
  )
}
var staticRenderFns = []
render._withStripped = true

if (false) {
  module.hot.accept()
  if (module.hot.data) {
    require("vue-hot-reload-api")      .rerender("data-v-6947c15c", { render: render, staticRenderFns: staticRenderFns })
  }
}

/***/ }),
/* 316 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue__ = __webpack_require__(120);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue__);
/* harmony namespace reexport (unknown) */ for(var __WEBPACK_IMPORT_KEY__ in __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue__) if(__WEBPACK_IMPORT_KEY__ !== 'default') (function(key) { __webpack_require__.d(__webpack_exports__, key, function() { return __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue__[key]; }) }(__WEBPACK_IMPORT_KEY__));
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_vue_loader_lib_template_compiler_index_id_data_v_82b55926_hasScoped_true_optionsId_0_buble_transforms_node_modules_vue_loader_lib_selector_type_template_index_0_index_vue__ = __webpack_require__(326);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__node_modules_vue_loader_lib_runtime_component_normalizer__ = __webpack_require__(6);
var disposed = false
function injectStyle (context) {
  if (disposed) return
  __webpack_require__(317)
  __webpack_require__(319)
}
/* script */


/* template */

/* template functional */
var __vue_template_functional__ = false
/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-82b55926"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null

var Component = Object(__WEBPACK_IMPORTED_MODULE_2__node_modules_vue_loader_lib_runtime_component_normalizer__["a" /* default */])(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue___default.a,
  __WEBPACK_IMPORTED_MODULE_1__node_modules_vue_loader_lib_template_compiler_index_id_data_v_82b55926_hasScoped_true_optionsId_0_buble_transforms_node_modules_vue_loader_lib_selector_type_template_index_0_index_vue__["a" /* render */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_vue_loader_lib_template_compiler_index_id_data_v_82b55926_hasScoped_true_optionsId_0_buble_transforms_node_modules_vue_loader_lib_selector_type_template_index_0_index_vue__["b" /* staticRenderFns */],
  __vue_template_functional__,
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "dev\\pages\\fit\\views\\detail\\index.vue"

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-82b55926", Component.options)
  } else {
    hotAPI.reload("data-v-82b55926", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["default"] = (Component.exports);


/***/ }),
/* 317 */
/***/ (function(module, exports, __webpack_require__) {


var content = __webpack_require__(318);

if(typeof content === 'string') content = [[module.i, content, '']];

var transform;
var insertInto;



var options = {"hmr":true}

options.transform = transform
options.insertInto = undefined;

var update = __webpack_require__(5)(content, options);

if(content.locals) module.exports = content.locals;

if(false) {
	module.hot.accept("!!../../../../../node_modules/css-loader/index.js!../../../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"optionsId\":\"0\",\"vue\":true,\"id\":\"data-v-82b55926\",\"scoped\":true,\"sourceMap\":false}!../../../../../node_modules/postcss-loader/lib/index.js!../../../../../node_modules/less-loader/dist/cjs.js!../../../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./index.vue", function() {
		var newContent = require("!!../../../../../node_modules/css-loader/index.js!../../../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"optionsId\":\"0\",\"vue\":true,\"id\":\"data-v-82b55926\",\"scoped\":true,\"sourceMap\":false}!../../../../../node_modules/postcss-loader/lib/index.js!../../../../../node_modules/less-loader/dist/cjs.js!../../../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./index.vue");

		if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];

		var locals = (function(a, b) {
			var key, idx = 0;

			for(key in a) {
				if(!b || a[key] !== b[key]) return false;
				idx++;
			}

			for(key in b) idx--;

			return idx === 0;
		}(content.locals, newContent.locals));

		if(!locals) throw new Error('Aborting CSS HMR due to changed css-modules locals.');

		update(newContent);
	});

	module.hot.dispose(function() { update(); });
}

/***/ }),
/* 318 */
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(3)(false);
// imports


// module
exports.push([module.i, "\n.create-comment[data-v-82b55926] {\n  float: right;\n  font-size: 12px;\n  background: #FFD52F;\n  color: #000;\n  border-radius: 100px;\n  padding: 2px 10px;\n  cursor: pointer;\n}\n.act-title[data-v-82b55926] {\n  background: #fff;\n  /*height: 1.3rem;*/\n  padding: 0.15rem;\n}\n.act-title .act-name[data-v-82b55926] {\n  font-size: 20px;\n  color: #000000;\n  line-height: 0.28rem;\n  font-weight: 600;\n}\n.act-title .time-bar[data-v-82b55926] {\n  height: 0.27rem;\n  line-height: 0.27rem;\n  font-size: 14px;\n  display: -moz-box;\n  display: flex;\n  margin-top: 0.06rem;\n}\n.act-title .type[data-v-82b55926] {\n  width: 30%;\n  background: #ffd52f;\n  text-align: center;\n  font-weight: 600;\n}\n.act-title .during[data-v-82b55926] {\n  width: 70%;\n  background: #464646;\n  color: #fff;\n  padding-left: 0.3rem;\n}\n.section[data-v-82b55926] {\n  background: #fff;\n  border-top: 10px solid #f6f6f6;\n  padding: 0.15rem;\n}\n.section h3[data-v-82b55926] {\n  padding-bottom: 0.1rem;\n  font-size: 16px;\n  font-weight: 400;\n}\n.act-title[data-v-82b55926] {\n  background: #fff;\n  min-height: 1.2rem;\n  padding: 0.15rem;\n  border-bottom: 1px solid #ececec;\n}\n.act-title .act-name[data-v-82b55926] {\n  font-size: 20px;\n  color: #000000;\n  line-height: 0.28rem;\n  font-weight: 600;\n}\n.publisher[data-v-82b55926] {\n  height: 0.85rem;\n  display: -moz-box;\n  display: flex;\n}\n.avatar[data-v-82b55926] {\n  width: 0.3rem;\n  height: 0.3rem;\n  border-radius: 100%;\n  vertical-align: middle;\n  display: inline-block;\n}\n.pubulisher-avatar[data-v-82b55926] {\n  width: 0.54rem;\n  height: 0.54rem;\n}\n.publisher-info[data-v-82b55926] {\n  margin-left: 0.15rem;\n  -moz-box-flex: 1;\n       flex-grow: 1;\n  font-size: 12px;\n  color: #9b9b9b;\n}\n.publisher-info .publisher-name[data-v-82b55926] {\n  font-size: 14px;\n  color: #4a4a4a;\n}\n.info[data-v-82b55926] {\n  margin-bottom: 3px;\n  font-size: 14px;\n  color: #fff;\n}\n.info span[data-v-82b55926] {\n  display: inline-block;\n  padding: 0 5px;\n  line-height: 22px;\n}\n.info span[data-v-82b55926]:first-child {\n  color: #000;\n  background: #FFD52F;\n}\n.info span[data-v-82b55926]:last-child {\n  color: #fff;\n  background: #464646;\n}\n.rank-item[data-v-82b55926] {\n  height: 0.6rem;\n  border-bottom: 1px solid #ECECEC;\n  padding: 0 0.2rem;\n  margin-bottom: 0.1rem;\n}\n.rank-item .rank-index[data-v-82b55926] {\n  line-height: 0.6rem;\n  font-size: 20px;\n  color: #4A4A4A;\n  display: inline-block;\n}\n.rank-item .rank-avatar[data-v-82b55926] {\n  width: 40px;\n  height: 40px;\n  border-radius: 40px;\n  margin-top: 10px;\n  margin-left: 10px;\n}\n.rank-item .rank-info[data-v-82b55926] {\n  text-align: left;\n  padding-top: 0.08rem;\n  padding-left: 0.2rem;\n}\n.rank-item .name[data-v-82b55926] {\n  font-size: 16px;\n  color: #4A4A4A;\n}\n.rank-item .rank-num[data-v-82b55926] {\n  padding-top: 0.05rem;\n  text-align: right;\n}\n.rank-item .num[data-v-82b55926] {\n  font-size: 32px;\n  color: #4A4A4A;\n  line-height: 35px;\n}\n.rank-item[data-v-82b55926]:nth-child(1) {\n  background: #F6E7AB;\n  border-radius: 8px;\n  border: none;\n}\n.rank-item[data-v-82b55926]:nth-child(2) {\n  background: #E7E7E7;\n  border-radius: 8px;\n  border: none;\n}\n.rank-item[data-v-82b55926]:nth-child(3) {\n  background: #F6CA9B;\n  border-radius: 8px;\n  border: none;\n}\n.button-container[data-v-82b55926] {\n  position: fixed;\n  bottom: 0;\n  z-index: 100;\n  width: 100%;\n  background: #fff;\n  border-top: 1px solid #e6e6e6;\n}\n.button-container .status[data-v-82b55926] {\n  background: white;\n  text-align: center;\n  height: 0.5rem;\n  line-height: 0.5rem;\n  font-weight: bold;\n}\n.button-container .check[data-v-82b55926] {\n  display: block;\n  text-align: center;\n  background: #FFD52F;\n  height: 0.5rem;\n  line-height: 0.5rem;\n}\n.button-container .check-end[data-v-82b55926] {\n  background: #bbb;\n}\n.showtip[data-v-82b55926] {\n  color: #a6a6a6;\n  width: 0.6rem;\n  height: 0.5rem;\n  background: transparent;\n  border: 0;\n}\n", ""]);

// exports


/***/ }),
/* 319 */
/***/ (function(module, exports, __webpack_require__) {


var content = __webpack_require__(320);

if(typeof content === 'string') content = [[module.i, content, '']];

var transform;
var insertInto;



var options = {"hmr":true}

options.transform = transform
options.insertInto = undefined;

var update = __webpack_require__(5)(content, options);

if(content.locals) module.exports = content.locals;

if(false) {
	module.hot.accept("!!../../../../../node_modules/css-loader/index.js!../../../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"optionsId\":\"0\",\"vue\":true,\"scoped\":false,\"sourceMap\":false}!../../../../../node_modules/postcss-loader/lib/index.js!../../../../../node_modules/less-loader/dist/cjs.js!../../../../../node_modules/vue-loader/lib/selector.js?type=styles&index=1!./index.vue", function() {
		var newContent = require("!!../../../../../node_modules/css-loader/index.js!../../../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"optionsId\":\"0\",\"vue\":true,\"scoped\":false,\"sourceMap\":false}!../../../../../node_modules/postcss-loader/lib/index.js!../../../../../node_modules/less-loader/dist/cjs.js!../../../../../node_modules/vue-loader/lib/selector.js?type=styles&index=1!./index.vue");

		if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];

		var locals = (function(a, b) {
			var key, idx = 0;

			for(key in a) {
				if(!b || a[key] !== b[key]) return false;
				idx++;
			}

			for(key in b) idx--;

			return idx === 0;
		}(content.locals, newContent.locals));

		if(!locals) throw new Error('Aborting CSS HMR due to changed css-modules locals.');

		update(newContent);
	});

	module.hot.dispose(function() { update(); });
}

/***/ }),
/* 320 */
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(3)(false);
// imports


// module
exports.push([module.i, "\n.popover-inner {\n  background-color: rgba(0, 0, 0, 0.6);\n  color: #fff;\n  width: 74px;\n  line-height: 30px;\n  text-align: center;\n  border-radius: 3px;\n}\n.popover-arrow {\n  border-color: rgba(0, 0, 0, 0.6);\n}\n", ""]);

// exports


/***/ }),
/* 321 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _regenerator = __webpack_require__(0);

var _regenerator2 = _interopRequireDefault(_regenerator);

var _assign = __webpack_require__(4);

var _assign2 = _interopRequireDefault(_assign);

var _asyncToGenerator2 = __webpack_require__(1);

var _asyncToGenerator3 = _interopRequireDefault(_asyncToGenerator2);

var _utils = __webpack_require__(2);

var _api = __webpack_require__(8);

var _api2 = _interopRequireDefault(_api);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

/**
 * the response likes below:
 * {
"activityId": 0,
"avatar": "string",
"id": 0,
"nickName": "string",
"participationTime": "2018-06-19T16:24:33.361Z",
"project": "string",
"wechatUserId": "string"
}
 *
 *
 */
exports.default = function () {
  var _ref = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee(id) {
    var url, _params, res;

    return _regenerator2.default.wrap(function _callee$(_context) {
      while (1) {
        switch (_context.prev = _context.next) {
          case 0:
            url = _api2.default.deleteActivityParticipation.url.replace('{{id}}', id);
            _params = (0, _assign2.default)({}, _api2.default.deleteActivityParticipation, {
              url: url
            });
            _context.next = 4;
            return (0, _utils.fetchAPI)(_params);

          case 4:
            res = _context.sent;


            console.log('deleteActivityParticipation response: ', res);

            return _context.abrupt('return', res);

          case 7:
          case 'end':
            return _context.stop();
        }
      }
    }, _callee, this);
  }));

  function deleteActivityParticipation(_x) {
    return _ref.apply(this, arguments);
  }

  return deleteActivityParticipation;
}();

/***/ }),
/* 322 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _regenerator = __webpack_require__(0);

var _regenerator2 = _interopRequireDefault(_regenerator);

var _assign = __webpack_require__(4);

var _assign2 = _interopRequireDefault(_assign);

var _asyncToGenerator2 = __webpack_require__(1);

var _asyncToGenerator3 = _interopRequireDefault(_asyncToGenerator2);

var _utils = __webpack_require__(2);

var _api = __webpack_require__(8);

var _api2 = _interopRequireDefault(_api);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

/**
 * the response likes below:
 * {
"activityEndTime": "2018-06-19T16:24:33.499Z",
"activityStartTime": "2018-06-19T16:24:33.499Z",
"avatar": "string",
"companyRole": "string",
"descrption": "string",
"id": 0,
"nickName": "string",
"project": "string",
"signEndTime": "2018-06-19T16:24:33.500Z",
"signStartTime": "2018-06-19T16:24:33.500Z",
"title": "string",
"wechatUserId": "string"
}
 *
 *
 */
exports.default = function () {
  var _ref = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee(id) {
    var url, _params, res;

    return _regenerator2.default.wrap(function _callee$(_context) {
      while (1) {
        switch (_context.prev = _context.next) {
          case 0:
            /**
             * 
            params = {
            "activityEndTime": "2018-06-19T16:24:33.504Z",
            "activityStartTime": "2018-06-19T16:24:33.504Z",
            "avatar": "string",
            "companyRole": "string",
            "descrption": "string",
            "id": 0,
            "nickName": "string",
            "project": "string",
            "signEndTime": "2018-06-19T16:24:33.504Z",
            "signStartTime": "2018-06-19T16:24:33.504Z",
            "title": "string",
            "wechatUserId": "string"
            }
            **/

            url = _api2.default.deleteFitnessActivity.url.replace('{{id}}', id);
            _params = (0, _assign2.default)({}, _api2.default.deleteFitnessActivity, {
              url: url
            });
            _context.next = 4;
            return (0, _utils.fetchAPI)(_params);

          case 4:
            res = _context.sent;


            console.log('deleteFitnessActivity response: ', res);

            return _context.abrupt('return', res);

          case 7:
          case 'end':
            return _context.stop();
        }
      }
    }, _callee, this);
  }));

  function deleteFitnessActivity(_x) {
    return _ref.apply(this, arguments);
  }

  return deleteFitnessActivity;
}();

/***/ }),
/* 323 */,
/* 324 */,
/* 325 */,
/* 326 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return render; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "b", function() { return staticRenderFns; });
var render = function() {
  var _vm = this
  var _h = _vm.$createElement
  var _c = _vm._self._c || _h
  return _vm.isLoaded
    ? _c(
        "div",
        [
          _vm.showImg
            ? _c("big-img", {
                attrs: { imgSrc: _vm.imgSrc },
                on: { clickit: _vm.viewImg }
              })
            : _vm._e(),
          _vm._v(" "),
          _c("swiper", { attrs: { image: _vm.images } }),
          _vm._v(" "),
          _c("div", { staticClass: "act-title" }, [
            _c("p", { staticClass: "act-name" }, [
              _vm._v(_vm._s(_vm.activity.title))
            ]),
            _vm._v(" "),
            _c("div", { staticClass: "info mt10" }, [
              _c("span", [_vm._v("活动时间")]),
              _c("span", [
                _vm._v(
                  _vm._s(
                    _vm.formatDate(_vm.activity.activityStartTime) +
                      "~" +
                      _vm.formatDate(_vm.activity.activityEndTime)
                  )
                )
              ])
            ])
          ]),
          _vm._v(" "),
          _c("attendee", { attrs: { list: _vm.join, attendText: "报名" } }),
          _vm._v(" "),
          _c("div", { staticClass: "section publisher" }, [
            _c("img", {
              staticClass: "avatar pubulisher-avatar",
              attrs: { src: _vm.activity.avatar, alt: "" }
            }),
            _vm._v(" "),
            _c("div", { staticClass: "publisher-info" }, [
              _c("p", { staticClass: "publisher-name" }, [
                _vm._v(
                  "发起人：" +
                    _vm._s(_vm._f("decodeBase64")(_vm.activity.nickName))
                )
              ]),
              _vm._v(" "),
              _c("p", [_vm._v("公司角色：--")]),
              _vm._v(" "),
              _c("p", [_vm._v("所属项目：--")])
            ])
          ]),
          _vm._v(" "),
          _c("div", { staticClass: "section" }, [
            _c("h3", [_vm._v("详情")]),
            _vm._v(" "),
            _c("pre", {
              staticClass: "fs12",
              staticStyle: { "margin-top": "0" },
              domProps: { innerHTML: _vm._s(_vm.activity.descrption) }
            })
          ]),
          _vm._v(" "),
          _c("div", { staticClass: "section" }, [
            _c("h3", [_vm._v("排行榜")]),
            _vm._v(" "),
            _c(
              "div",
              { staticClass: "rank" },
              [
                _vm._l(_vm.join, function(item, index) {
                  return _vm.join.length
                    ? _c(
                        "Row",
                        { key: index, staticClass: "rank-item" },
                        [
                          _c(
                            "router-link",
                            {
                              attrs: {
                                to:
                                  "/record?wechatUserId=" +
                                  item.wechatUserId +
                                  "&activity=" +
                                  _vm.activity.id +
                                  "&rate=" +
                                  index
                              }
                            },
                            [
                              _c(
                                "Col",
                                {
                                  staticClass: "rank-index",
                                  attrs: { span: "2" }
                                },
                                [_vm._v(_vm._s(index + 1))]
                              ),
                              _vm._v(" "),
                              _c("Col", { attrs: { span: "4" } }, [
                                _c("img", {
                                  staticClass: "rank-avatar",
                                  attrs: { src: item.avatar, alt: "" }
                                })
                              ]),
                              _vm._v(" "),
                              _c(
                                "Col",
                                {
                                  staticClass: "black rank-info",
                                  attrs: { span: "10" }
                                },
                                [
                                  _c("div", { staticClass: "name" }, [
                                    _vm._v(
                                      _vm._s(
                                        _vm._f("decodeBase64")(item.nickName)
                                      )
                                    )
                                  ]),
                                  _vm._v(" "),
                                  _c("div", [_vm._v(_vm._s(item.project))])
                                ]
                              ),
                              _vm._v(" "),
                              _c(
                                "Col",
                                {
                                  staticClass: "rank-num",
                                  attrs: { span: "8" }
                                },
                                [
                                  _c("div", { staticClass: "num" }, [
                                    _vm._v(_vm._s(item.clockinCount))
                                  ]),
                                  _vm._v(" "),
                                  _c("div", { staticClass: "fs12 black" }, [
                                    _vm._v("打卡次数")
                                  ])
                                ]
                              )
                            ],
                            1
                          )
                        ],
                        1
                      )
                    : _vm._e()
                }),
                _vm._v(" "),
                !_vm.join.length ? _c("p", [_vm._v("暂无排行榜")]) : _vm._e()
              ],
              2
            )
          ]),
          _vm._v(" "),
          _c(
            "div",
            { staticClass: "section" },
            [
              _c(
                "h3",
                [
                  _vm._v("讨论区(" + _vm._s(_vm.list.length) + ")\n      "),
                  _c(
                    "router-link",
                    {
                      staticClass: "create-comment",
                      attrs: { to: { path: "/discuss/" + _vm.id } }
                    },
                    [_vm._v("发讨论")]
                  )
                ],
                1
              ),
              _vm._v(" "),
              _c("Comments", { attrs: { list: _vm.list } })
            ],
            1
          ),
          _vm._v(" "),
          _c(
            "div",
            { staticClass: "button-container" },
            [
              _c(
                "Row",
                [
                  _c("Col", { attrs: { span: "14" } }, [
                    _vm.overdue === 2
                      ? _c("span", { staticClass: "status pl15" }, [
                          _vm._v("活动已结束")
                        ])
                      : _vm._e(),
                    _vm._v(" "),
                    _vm.overdue === 1
                      ? _c("span", { staticClass: "status pl15" }, [
                          _vm._v("活动进行中")
                        ])
                      : _vm._e(),
                    _vm._v(" "),
                    _vm.overdue === 0
                      ? _c("span", { staticClass: "status pl15" }, [
                          _vm._v("活动未开始")
                        ])
                      : _vm._e()
                  ]),
                  _vm._v(" "),
                  _c(
                    "Col",
                    {
                      staticStyle: { "border-left": "1px solid #dadada" },
                      attrs: { span: "4" }
                    },
                    [
                      _vm.overdue != 2
                        ? _c(
                            "v-popover",
                            {
                              staticClass: "showtip-wrap",
                              attrs: { offset: "16" }
                            },
                            [
                              _c(
                                "button",
                                { staticClass: "tooltip-target showtip" },
                                [_vm._v("···")]
                              ),
                              _vm._v(" "),
                              _c("template", { slot: "popover" }, [
                                !_vm.isMine &&
                                !_vm.isNotJoined &&
                                _vm.overdue != 2
                                  ? _c(
                                      "span",
                                      { on: { click: _vm.cancelApply } },
                                      [_vm._v("取消报名")]
                                    )
                                  : _vm._e(),
                                _vm._v(" "),
                                _vm.isMine
                                  ? _c(
                                      "div",
                                      { on: { click: _vm.editActivity } },
                                      [_vm._v("编辑活动")]
                                    )
                                  : _vm._e()
                              ])
                            ],
                            2
                          )
                        : _vm._e()
                    ],
                    1
                  ),
                  _vm._v(" "),
                  _c(
                    "Col",
                    { attrs: { span: "6" } },
                    [
                      !_vm.isNotJoined && _vm.overdue === 1
                        ? _c(
                            "router-link",
                            {
                              staticClass: "check black",
                              attrs: { to: "/check/" + _vm.activity.id }
                            },
                            [_vm._v("去打卡")]
                          )
                        : _vm._e(),
                      _vm._v(" "),
                      _vm.isNotJoined && _vm.overdue !== 2
                        ? _c(
                            "p",
                            {
                              staticClass: "check black",
                              on: { click: _vm.joinActivities }
                            },
                            [_vm._v("去报名")]
                          )
                        : _vm._e(),
                      _vm._v(" "),
                      !_vm.isNotJoined && _vm.overdue === 0
                        ? _c("p", { staticClass: "check-end check black" }, [
                            _vm._v("去打卡")
                          ])
                        : _vm._e(),
                      _vm._v(" "),
                      _vm.overdue === 2
                        ? _c("p", { staticClass: "check black check-end" }, [
                            _vm._v("活动结束")
                          ])
                        : _vm._e()
                    ],
                    1
                  )
                ],
                1
              )
            ],
            1
          )
        ],
        1
      )
    : _vm._e()
}
var staticRenderFns = []
render._withStripped = true

if (false) {
  module.hot.accept()
  if (module.hot.data) {
    require("vue-hot-reload-api")      .rerender("data-v-82b55926", { render: render, staticRenderFns: staticRenderFns })
  }
}

/***/ }),
/* 327 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue__ = __webpack_require__(125);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue__);
/* harmony namespace reexport (unknown) */ for(var __WEBPACK_IMPORT_KEY__ in __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue__) if(__WEBPACK_IMPORT_KEY__ !== 'default') (function(key) { __webpack_require__.d(__webpack_exports__, key, function() { return __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue__[key]; }) }(__WEBPACK_IMPORT_KEY__));
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_vue_loader_lib_template_compiler_index_id_data_v_09bbd262_hasScoped_true_optionsId_0_buble_transforms_node_modules_vue_loader_lib_selector_type_template_index_0_index_vue__ = __webpack_require__(331);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__node_modules_vue_loader_lib_runtime_component_normalizer__ = __webpack_require__(6);
var disposed = false
function injectStyle (context) {
  if (disposed) return
  __webpack_require__(328)
}
/* script */


/* template */

/* template functional */
var __vue_template_functional__ = false
/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-09bbd262"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null

var Component = Object(__WEBPACK_IMPORTED_MODULE_2__node_modules_vue_loader_lib_runtime_component_normalizer__["a" /* default */])(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue___default.a,
  __WEBPACK_IMPORTED_MODULE_1__node_modules_vue_loader_lib_template_compiler_index_id_data_v_09bbd262_hasScoped_true_optionsId_0_buble_transforms_node_modules_vue_loader_lib_selector_type_template_index_0_index_vue__["a" /* render */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_vue_loader_lib_template_compiler_index_id_data_v_09bbd262_hasScoped_true_optionsId_0_buble_transforms_node_modules_vue_loader_lib_selector_type_template_index_0_index_vue__["b" /* staticRenderFns */],
  __vue_template_functional__,
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "dev\\pages\\fit\\views\\publish\\index.vue"

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-09bbd262", Component.options)
  } else {
    hotAPI.reload("data-v-09bbd262", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["default"] = (Component.exports);


/***/ }),
/* 328 */
/***/ (function(module, exports, __webpack_require__) {


var content = __webpack_require__(329);

if(typeof content === 'string') content = [[module.i, content, '']];

var transform;
var insertInto;



var options = {"hmr":true}

options.transform = transform
options.insertInto = undefined;

var update = __webpack_require__(5)(content, options);

if(content.locals) module.exports = content.locals;

if(false) {
	module.hot.accept("!!../../../../../node_modules/css-loader/index.js!../../../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"optionsId\":\"0\",\"vue\":true,\"id\":\"data-v-09bbd262\",\"scoped\":true,\"sourceMap\":false}!../../../../../node_modules/postcss-loader/lib/index.js!../../../../../node_modules/less-loader/dist/cjs.js!../../../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./index.vue", function() {
		var newContent = require("!!../../../../../node_modules/css-loader/index.js!../../../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"optionsId\":\"0\",\"vue\":true,\"id\":\"data-v-09bbd262\",\"scoped\":true,\"sourceMap\":false}!../../../../../node_modules/postcss-loader/lib/index.js!../../../../../node_modules/less-loader/dist/cjs.js!../../../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./index.vue");

		if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];

		var locals = (function(a, b) {
			var key, idx = 0;

			for(key in a) {
				if(!b || a[key] !== b[key]) return false;
				idx++;
			}

			for(key in b) idx--;

			return idx === 0;
		}(content.locals, newContent.locals));

		if(!locals) throw new Error('Aborting CSS HMR due to changed css-modules locals.');

		update(newContent);
	});

	module.hot.dispose(function() { update(); });
}

/***/ }),
/* 329 */
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(3)(false);
// imports


// module
exports.push([module.i, "\n.publish[data-v-09bbd262] {\n  background: #fff;\n  width: 100%;\n  margin-bottom: 50px;\n}\n.publish .publish-item[data-v-09bbd262] {\n  border-bottom: 1px solid #E0E0E0;\n  height: 0.5rem;\n  line-height: 0.5rem;\n  padding: 0 0.15rem;\n  display: -moz-box;\n  display: flex;\n  position: relative;\n}\n.publish .publish-item .form-item[data-v-09bbd262] {\n  height: 0.3rem;\n  margin: 0.1rem 0;\n  padding: 0 0.1rem;\n  line-height: 0.3rem;\n  border: 0;\n  -moz-box-flex: 1;\n       flex: 1;\n}\n.publish .publish-item .comment-text[data-v-09bbd262] {\n  width: 100%;\n  height: 80px;\n  line-height: 16px;\n  font-size: 14px;\n  margin: 0;\n}\n.publish .item-description[data-v-09bbd262] {\n  display: block;\n  height: 150px;\n  border: none;\n}\n.publish .comment[data-v-09bbd262] {\n  font-size: 12px;\n  color: #9A9A9A;\n}\n.publish .food-img[data-v-09bbd262] {\n  height: 100px;\n  width: 100%;\n}\n.publish .btn[data-v-09bbd262] {\n  position: relative;\n  width: 93%;\n  height: 44px;\n  background: #FFD52F;\n  box-shadow: 0 0 2px 0 rgba(0, 0, 0, 0.2);\n  border-radius: 30px;\n  border: none;\n  font-size: 16px;\n  color: #000000;\n}\n.publish .arrow-forward[data-v-09bbd262] {\n  font-size: 14px;\n  position: absolute;\n  right: 20px;\n  top: 20px;\n}\n.publish .icon[data-v-09bbd262] {\n  position: absolute;\n  left: 39%;\n  top: 20%;\n}\n.publish .fg1[data-v-09bbd262] {\n  -moz-box-flex: 1;\n       flex-grow: 1;\n}\n", ""]);

// exports


/***/ }),
/* 330 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _regenerator = __webpack_require__(0);

var _regenerator2 = _interopRequireDefault(_regenerator);

var _assign = __webpack_require__(4);

var _assign2 = _interopRequireDefault(_assign);

var _asyncToGenerator2 = __webpack_require__(1);

var _asyncToGenerator3 = _interopRequireDefault(_asyncToGenerator2);

var _utils = __webpack_require__(2);

var _api = __webpack_require__(8);

var _api2 = _interopRequireDefault(_api);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

/**
 * the response likes below:
 * {
  }
 *
 *
 */
exports.default = function () {
  var _ref = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee(wechatUserId) {
    var url, _params, res;

    return _regenerator2.default.wrap(function _callee$(_context) {
      while (1) {
        switch (_context.prev = _context.next) {
          case 0:
            url = _api2.default.viewCreateFitnessActivity.url.replace('{{wechatUserId}}', wechatUserId);
            _params = (0, _assign2.default)({}, _api2.default.viewCreateFitnessActivity, {
              url: url
            });
            _context.next = 4;
            return (0, _utils.fetchAPI)(_params);

          case 4:
            res = _context.sent;


            console.log('viewCreateFitnessActivity response: ', res);

            return _context.abrupt('return', res);

          case 7:
          case 'end':
            return _context.stop();
        }
      }
    }, _callee, this);
  }));

  function viewCreateFitnessActivity(_x) {
    return _ref.apply(this, arguments);
  }

  return viewCreateFitnessActivity;
}();

/***/ }),
/* 331 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return render; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "b", function() { return staticRenderFns; });
var render = function() {
  var _vm = this
  var _h = _vm.$createElement
  var _c = _vm._self._c || _h
  return _c(
    "div",
    { staticClass: "publish" },
    [
      _c("div", { staticClass: "publish-item" }, [
        _c("span", { staticClass: "title" }, [_vm._v("标题")]),
        _vm._v(" "),
        _c("input", {
          directives: [
            {
              name: "model",
              rawName: "v-model",
              value: _vm.title,
              expression: "title"
            }
          ],
          staticClass: "form-item",
          attrs: { type: "text tr" },
          domProps: { value: _vm.title },
          on: {
            input: function($event) {
              if ($event.target.composing) {
                return
              }
              _vm.title = $event.target.value
            }
          }
        })
      ]),
      _vm._v(" "),
      _c("div", { staticClass: "publish-item" }, [
        _c("span", { staticClass: "title fg1" }, [_vm._v("活动开始时间")]),
        _vm._v(" "),
        _c(
          "label",
          { staticClass: "tr fg1", attrs: { for: "activityStartTime" } },
          [_vm._v(_vm._s(_vm.activityStartTime))]
        ),
        _vm._v(" "),
        _c("input", {
          directives: [
            {
              name: "model",
              rawName: "v-model",
              value: _vm.activityStartTime,
              expression: "activityStartTime"
            }
          ],
          staticClass: "form-item tr",
          staticStyle: { opacity: "0", position: "absolute" },
          attrs: {
            id: "activityStartTime",
            type: "date",
            placeholder: "选择开始时间"
          },
          domProps: { value: _vm.activityStartTime },
          on: {
            input: function($event) {
              if ($event.target.composing) {
                return
              }
              _vm.activityStartTime = $event.target.value
            }
          }
        })
      ]),
      _vm._v(" "),
      _c("div", { staticClass: "publish-item" }, [
        _c("span", { staticClass: "title fg1" }, [_vm._v("活动持续时间")]),
        _vm._v(" "),
        _c("input", {
          directives: [
            {
              name: "model",
              rawName: "v-model",
              value: _vm.lastDay,
              expression: "lastDay"
            }
          ],
          staticClass: "form-item tr",
          attrs: { type: "tel", placeholder: "选择持续时间" },
          domProps: { value: _vm.lastDay },
          on: {
            input: function($event) {
              if ($event.target.composing) {
                return
              }
              _vm.lastDay = $event.target.value
            }
          }
        }),
        _vm._v(" "),
        _c("span", [_vm._v("天")])
      ]),
      _vm._v(" "),
      _vm._m(0),
      _vm._v(" "),
      _c(
        "div",
        { staticClass: "publish-item", staticStyle: { border: "none" } },
        [
          _c("Upload", {
            ref: "upload",
            attrs: { finishUpload: _vm.finishUpload }
          })
        ],
        1
      ),
      _vm._v(" "),
      _c("div", { staticClass: "publish-item item-photo" }),
      _vm._v(" "),
      _c("div", { staticClass: "publish-item item-description" }, [
        _c("p", { staticClass: "title" }, [_vm._v("活动描述")]),
        _vm._v(" "),
        _c("textarea", {
          directives: [
            {
              name: "model",
              rawName: "v-model",
              value: _vm.description,
              expression: "description"
            }
          ],
          staticClass: "form-item comment-text",
          attrs: { placeholder: "准备了哪什么活动给你的小伙伴呢?" },
          domProps: { value: _vm.description },
          on: {
            input: function($event) {
              if ($event.target.composing) {
                return
              }
              _vm.description = $event.target.value
            }
          }
        })
      ]),
      _vm._v(" "),
      _c(
        "div",
        {
          staticStyle: {
            width: "100%",
            "text-align": "center",
            padding: "0.15rem 0"
          }
        },
        [
          _c(
            "button",
            { staticClass: "btn", on: { click: _vm.submit } },
            [
              _c("Icon", {
                staticClass: "icon",
                attrs: { type: "ios-paperplane-outline", size: "30" }
              }),
              _vm._v("\n      发布\n    ")
            ],
            1
          )
        ]
      ),
      _vm._v(" "),
      _c("Modal", { ref: "modal", attrs: { dialog: _vm.dialog } })
    ],
    1
  )
}
var staticRenderFns = [
  function() {
    var _vm = this
    var _h = _vm.$createElement
    var _c = _vm._self._c || _h
    return _c(
      "div",
      { staticClass: "publish-item", staticStyle: { border: "none" } },
      [
        _c("span", { staticClass: "title fg1" }, [_vm._v("封面图")]),
        _vm._v(" "),
        _c("span", { staticClass: "comment" }, [_vm._v("请上传封面图片")])
      ]
    )
  }
]
render._withStripped = true

if (false) {
  module.hot.accept()
  if (module.hot.data) {
    require("vue-hot-reload-api")      .rerender("data-v-09bbd262", { render: render, staticRenderFns: staticRenderFns })
  }
}

/***/ }),
/* 332 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue__ = __webpack_require__(127);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue__);
/* harmony namespace reexport (unknown) */ for(var __WEBPACK_IMPORT_KEY__ in __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue__) if(__WEBPACK_IMPORT_KEY__ !== 'default') (function(key) { __webpack_require__.d(__webpack_exports__, key, function() { return __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue__[key]; }) }(__WEBPACK_IMPORT_KEY__));
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_vue_loader_lib_template_compiler_index_id_data_v_3d79d649_hasScoped_true_optionsId_0_buble_transforms_node_modules_vue_loader_lib_selector_type_template_index_0_index_vue__ = __webpack_require__(336);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__node_modules_vue_loader_lib_runtime_component_normalizer__ = __webpack_require__(6);
var disposed = false
function injectStyle (context) {
  if (disposed) return
  __webpack_require__(333)
}
/* script */


/* template */

/* template functional */
var __vue_template_functional__ = false
/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-3d79d649"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null

var Component = Object(__WEBPACK_IMPORTED_MODULE_2__node_modules_vue_loader_lib_runtime_component_normalizer__["a" /* default */])(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue___default.a,
  __WEBPACK_IMPORTED_MODULE_1__node_modules_vue_loader_lib_template_compiler_index_id_data_v_3d79d649_hasScoped_true_optionsId_0_buble_transforms_node_modules_vue_loader_lib_selector_type_template_index_0_index_vue__["a" /* render */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_vue_loader_lib_template_compiler_index_id_data_v_3d79d649_hasScoped_true_optionsId_0_buble_transforms_node_modules_vue_loader_lib_selector_type_template_index_0_index_vue__["b" /* staticRenderFns */],
  __vue_template_functional__,
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "dev\\pages\\fit\\views\\PublishModify\\index.vue"

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-3d79d649", Component.options)
  } else {
    hotAPI.reload("data-v-3d79d649", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["default"] = (Component.exports);


/***/ }),
/* 333 */
/***/ (function(module, exports, __webpack_require__) {


var content = __webpack_require__(334);

if(typeof content === 'string') content = [[module.i, content, '']];

var transform;
var insertInto;



var options = {"hmr":true}

options.transform = transform
options.insertInto = undefined;

var update = __webpack_require__(5)(content, options);

if(content.locals) module.exports = content.locals;

if(false) {
	module.hot.accept("!!../../../../../node_modules/css-loader/index.js!../../../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"optionsId\":\"0\",\"vue\":true,\"id\":\"data-v-3d79d649\",\"scoped\":true,\"sourceMap\":false}!../../../../../node_modules/postcss-loader/lib/index.js!../../../../../node_modules/less-loader/dist/cjs.js!../../../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./index.vue", function() {
		var newContent = require("!!../../../../../node_modules/css-loader/index.js!../../../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"optionsId\":\"0\",\"vue\":true,\"id\":\"data-v-3d79d649\",\"scoped\":true,\"sourceMap\":false}!../../../../../node_modules/postcss-loader/lib/index.js!../../../../../node_modules/less-loader/dist/cjs.js!../../../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./index.vue");

		if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];

		var locals = (function(a, b) {
			var key, idx = 0;

			for(key in a) {
				if(!b || a[key] !== b[key]) return false;
				idx++;
			}

			for(key in b) idx--;

			return idx === 0;
		}(content.locals, newContent.locals));

		if(!locals) throw new Error('Aborting CSS HMR due to changed css-modules locals.');

		update(newContent);
	});

	module.hot.dispose(function() { update(); });
}

/***/ }),
/* 334 */
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(3)(false);
// imports


// module
exports.push([module.i, "\n.publish[data-v-3d79d649] {\n  background: #fff;\n  width: 100%;\n  margin-bottom: 50px;\n}\n.publish .publish-item[data-v-3d79d649] {\n  border-bottom: 1px solid #E0E0E0;\n  height: 0.5rem;\n  line-height: 0.5rem;\n  padding: 0 0.15rem;\n  display: -moz-box;\n  display: flex;\n  position: relative;\n}\n.publish .publish-item .form-item[data-v-3d79d649] {\n  height: 0.3rem;\n  margin: 0.1rem 0;\n  padding: 0 0.1rem;\n  line-height: 0.3rem;\n  border: 0;\n  -moz-box-flex: 1;\n       flex: 1;\n}\n.publish .publish-item .comment-text[data-v-3d79d649] {\n  width: 100%;\n  height: 80px;\n  line-height: 16px;\n  font-size: 14px;\n  margin: 0;\n}\n.publish .item-description[data-v-3d79d649] {\n  display: block;\n  height: 150px;\n  border: none;\n}\n.publish .comment[data-v-3d79d649] {\n  font-size: 12px;\n  color: #9A9A9A;\n}\n.publish .food-img[data-v-3d79d649] {\n  height: 100px;\n  width: 100%;\n}\n.publish .btn[data-v-3d79d649] {\n  position: relative;\n  width: 93%;\n  height: 44px;\n  background: #FFD52F;\n  box-shadow: 0 0 2px 0 rgba(0, 0, 0, 0.2);\n  border-radius: 30px;\n  border: none;\n  font-size: 16px;\n  color: #000000;\n}\n.publish .arrow-forward[data-v-3d79d649] {\n  font-size: 14px;\n  position: absolute;\n  right: 20px;\n  top: 20px;\n}\n.publish .icon[data-v-3d79d649] {\n  position: absolute;\n  left: 39%;\n  top: 20%;\n}\n.publish .fg1[data-v-3d79d649] {\n  -moz-box-flex: 1;\n       flex-grow: 1;\n}\n", ""]);

// exports


/***/ }),
/* 335 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _regenerator = __webpack_require__(0);

var _regenerator2 = _interopRequireDefault(_regenerator);

var _assign = __webpack_require__(4);

var _assign2 = _interopRequireDefault(_assign);

var _asyncToGenerator2 = __webpack_require__(1);

var _asyncToGenerator3 = _interopRequireDefault(_asyncToGenerator2);

var _utils = __webpack_require__(2);

var _api = __webpack_require__(8);

var _api2 = _interopRequireDefault(_api);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

/**
 * the response likes below:
 * {
"activityEndTime": "2018-06-19T16:24:33.499Z",
"activityStartTime": "2018-06-19T16:24:33.499Z",
"avatar": "string",
"companyRole": "string",
"descrption": "string",
"id": 0,
"nickName": "string",
"project": "string",
"signEndTime": "2018-06-19T16:24:33.500Z",
"signStartTime": "2018-06-19T16:24:33.500Z",
"title": "string",
"wechatUserId": "string"
}
 *
 *
 */
exports.default = function () {
  var _ref = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee() {
    var params = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : {};

    var url, _params, res;

    return _regenerator2.default.wrap(function _callee$(_context) {
      while (1) {
        switch (_context.prev = _context.next) {
          case 0:
            /**
             * 
            params = {
            "activityEndTime": "2018-06-19T16:24:33.504Z",
            "activityStartTime": "2018-06-19T16:24:33.504Z",
            "avatar": "string",
            "companyRole": "string",
            "descrption": "string",
            "id": 0,
            "nickName": "string",
            "project": "string",
            "signEndTime": "2018-06-19T16:24:33.504Z",
            "signStartTime": "2018-06-19T16:24:33.504Z",
            "title": "string",
            "wechatUserId": "string"
            }
            **/
            url = _api2.default.updateFitnessActivity.url;
            _params = (0, _assign2.default)({}, _api2.default.updateFitnessActivity, {
              url: url,
              body: params
            });
            _context.next = 4;
            return (0, _utils.fetchAPI)(_params);

          case 4:
            res = _context.sent;


            console.log('updateFitnessActivity response: ', res);

            return _context.abrupt('return', res);

          case 7:
          case 'end':
            return _context.stop();
        }
      }
    }, _callee, this);
  }));

  function updateFitnessActivity() {
    return _ref.apply(this, arguments);
  }

  return updateFitnessActivity;
}();

/***/ }),
/* 336 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return render; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "b", function() { return staticRenderFns; });
var render = function() {
  var _vm = this
  var _h = _vm.$createElement
  var _c = _vm._self._c || _h
  return _c(
    "div",
    { staticClass: "publish" },
    [
      _c("div", { staticClass: "publish-item" }, [
        _c("span", { staticClass: "title" }, [_vm._v("标题")]),
        _vm._v(" "),
        _c("input", {
          directives: [
            {
              name: "model",
              rawName: "v-model",
              value: _vm.title,
              expression: "title"
            }
          ],
          staticClass: "form-item",
          attrs: { type: "text tr" },
          domProps: { value: _vm.title },
          on: {
            input: function($event) {
              if ($event.target.composing) {
                return
              }
              _vm.title = $event.target.value
            }
          }
        })
      ]),
      _vm._v(" "),
      _c("div", { staticClass: "publish-item" }, [
        _c("span", { staticClass: "title fg1" }, [_vm._v("活动开始时间")]),
        _vm._v(" "),
        _c(
          "label",
          { staticClass: "tr fg1", attrs: { for: "activityStartTime" } },
          [_vm._v(_vm._s(_vm.activityStartTime))]
        ),
        _vm._v(" "),
        _c("input", {
          directives: [
            {
              name: "model",
              rawName: "v-model",
              value: _vm.activityStartTime,
              expression: "activityStartTime"
            }
          ],
          staticClass: "form-item tr",
          staticStyle: { opacity: "0", position: "absolute" },
          attrs: {
            id: "activityStartTime",
            type: "date",
            placeholder: "选择开始时间"
          },
          domProps: { value: _vm.activityStartTime },
          on: {
            input: function($event) {
              if ($event.target.composing) {
                return
              }
              _vm.activityStartTime = $event.target.value
            }
          }
        })
      ]),
      _vm._v(" "),
      _c("div", { staticClass: "publish-item" }, [
        _c("span", { staticClass: "title fg1" }, [_vm._v("活动持续时间")]),
        _vm._v(" "),
        _c("input", {
          directives: [
            {
              name: "model",
              rawName: "v-model",
              value: _vm.lastDay,
              expression: "lastDay"
            }
          ],
          staticClass: "form-item tr",
          attrs: { type: "tel", placeholder: "选择持续时间" },
          domProps: { value: _vm.lastDay },
          on: {
            input: function($event) {
              if ($event.target.composing) {
                return
              }
              _vm.lastDay = $event.target.value
            }
          }
        }),
        _vm._v(" "),
        _c("span", [_vm._v("天")])
      ]),
      _vm._v(" "),
      _vm._m(0),
      _vm._v(" "),
      _c(
        "div",
        { staticClass: "publish-item", staticStyle: { border: "none" } },
        [
          _c("Upload", {
            ref: "upload",
            attrs: { finishUpload: _vm.finishUpload }
          })
        ],
        1
      ),
      _vm._v(" "),
      _c("div", { staticClass: "publish-item item-photo" }),
      _vm._v(" "),
      _c("div", { staticClass: "publish-item item-description" }, [
        _c("p", { staticClass: "title" }, [_vm._v("活动描述")]),
        _vm._v(" "),
        _c("textarea", {
          directives: [
            {
              name: "model",
              rawName: "v-model",
              value: _vm.description,
              expression: "description"
            }
          ],
          staticClass: "form-item comment-text",
          attrs: { placeholder: "准备了哪什么活动给你的小伙伴呢?" },
          domProps: { value: _vm.description },
          on: {
            input: function($event) {
              if ($event.target.composing) {
                return
              }
              _vm.description = $event.target.value
            }
          }
        })
      ]),
      _vm._v(" "),
      _c(
        "div",
        {
          staticStyle: {
            width: "100%",
            "text-align": "center",
            padding: "0.15rem 0"
          }
        },
        [
          _c(
            "button",
            { staticClass: "btn", on: { click: _vm.submit } },
            [
              _c("Icon", {
                staticClass: "icon",
                attrs: { type: "ios-paperplane-outline", size: "30" }
              }),
              _vm._v("\n      发布\n    ")
            ],
            1
          )
        ]
      ),
      _vm._v(" "),
      _c("Modal", { ref: "modal", attrs: { dialog: _vm.dialog } })
    ],
    1
  )
}
var staticRenderFns = [
  function() {
    var _vm = this
    var _h = _vm.$createElement
    var _c = _vm._self._c || _h
    return _c(
      "div",
      { staticClass: "publish-item", staticStyle: { border: "none" } },
      [
        _c("span", { staticClass: "title fg1" }, [_vm._v("封面图")]),
        _vm._v(" "),
        _c("span", { staticClass: "comment" }, [_vm._v("请上传封面图片")])
      ]
    )
  }
]
render._withStripped = true

if (false) {
  module.hot.accept()
  if (module.hot.data) {
    require("vue-hot-reload-api")      .rerender("data-v-3d79d649", { render: render, staticRenderFns: staticRenderFns })
  }
}

/***/ }),
/* 337 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue__ = __webpack_require__(128);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue__);
/* harmony namespace reexport (unknown) */ for(var __WEBPACK_IMPORT_KEY__ in __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue__) if(__WEBPACK_IMPORT_KEY__ !== 'default') (function(key) { __webpack_require__.d(__webpack_exports__, key, function() { return __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue__[key]; }) }(__WEBPACK_IMPORT_KEY__));
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_vue_loader_lib_template_compiler_index_id_data_v_96f0dff0_hasScoped_true_optionsId_0_buble_transforms_node_modules_vue_loader_lib_selector_type_template_index_0_index_vue__ = __webpack_require__(341);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__node_modules_vue_loader_lib_runtime_component_normalizer__ = __webpack_require__(6);
var disposed = false
function injectStyle (context) {
  if (disposed) return
  __webpack_require__(338)
}
/* script */


/* template */

/* template functional */
var __vue_template_functional__ = false
/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-96f0dff0"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null

var Component = Object(__WEBPACK_IMPORTED_MODULE_2__node_modules_vue_loader_lib_runtime_component_normalizer__["a" /* default */])(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue___default.a,
  __WEBPACK_IMPORTED_MODULE_1__node_modules_vue_loader_lib_template_compiler_index_id_data_v_96f0dff0_hasScoped_true_optionsId_0_buble_transforms_node_modules_vue_loader_lib_selector_type_template_index_0_index_vue__["a" /* render */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_vue_loader_lib_template_compiler_index_id_data_v_96f0dff0_hasScoped_true_optionsId_0_buble_transforms_node_modules_vue_loader_lib_selector_type_template_index_0_index_vue__["b" /* staticRenderFns */],
  __vue_template_functional__,
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "dev\\pages\\fit\\views\\check\\index.vue"

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-96f0dff0", Component.options)
  } else {
    hotAPI.reload("data-v-96f0dff0", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["default"] = (Component.exports);


/***/ }),
/* 338 */
/***/ (function(module, exports, __webpack_require__) {


var content = __webpack_require__(339);

if(typeof content === 'string') content = [[module.i, content, '']];

var transform;
var insertInto;



var options = {"hmr":true}

options.transform = transform
options.insertInto = undefined;

var update = __webpack_require__(5)(content, options);

if(content.locals) module.exports = content.locals;

if(false) {
	module.hot.accept("!!../../../../../node_modules/css-loader/index.js!../../../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"optionsId\":\"0\",\"vue\":true,\"id\":\"data-v-96f0dff0\",\"scoped\":true,\"sourceMap\":false}!../../../../../node_modules/postcss-loader/lib/index.js!../../../../../node_modules/less-loader/dist/cjs.js!../../../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./index.vue", function() {
		var newContent = require("!!../../../../../node_modules/css-loader/index.js!../../../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"optionsId\":\"0\",\"vue\":true,\"id\":\"data-v-96f0dff0\",\"scoped\":true,\"sourceMap\":false}!../../../../../node_modules/postcss-loader/lib/index.js!../../../../../node_modules/less-loader/dist/cjs.js!../../../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./index.vue");

		if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];

		var locals = (function(a, b) {
			var key, idx = 0;

			for(key in a) {
				if(!b || a[key] !== b[key]) return false;
				idx++;
			}

			for(key in b) idx--;

			return idx === 0;
		}(content.locals, newContent.locals));

		if(!locals) throw new Error('Aborting CSS HMR due to changed css-modules locals.');

		update(newContent);
	});

	module.hot.dispose(function() { update(); });
}

/***/ }),
/* 339 */
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(3)(false);
// imports


// module
exports.push([module.i, "\n.check[data-v-96f0dff0] {\n  margin-bottom: 0.5rem;\n  background: #fff;\n}\n.check-img[data-v-96f0dff0] {\n  width: 100%;\n}\n.info-container[data-v-96f0dff0] {\n  padding: 0.15rem 0.2rem 0.15rem 0.2rem;\n  color: black;\n  border-bottom: 1px solid #E0E0E0;\n}\n.info-container .info[data-v-96f0dff0] {\n  padding-top: 0.05rem;\n  color: #A0A0A0;\n  line-height: 18px;\n}\n.diary-container[data-v-96f0dff0] {\n  padding: 0.2rem;\n}\n.diary-container .diary[data-v-96f0dff0] {\n  width: 100%;\n  margin-top: 0.1rem;\n  border: none;\n  background: transparent;\n}\n.upload-container[data-v-96f0dff0] {\n  padding: 0.15rem;\n}\n.button-container[data-v-96f0dff0] {\n  padding: 0.15rem;\n  /*height: 75px;*/\n  background: white;\n  position: fixed;\n  bottom: 0.5rem;\n  width: 100%;\n  /*margin-bottom: 0.5rem;*/\n}\n.button-container .check-button[data-v-96f0dff0] {\n  height: 45px;\n  background: #FFD52F;\n  border: none;\n  color: black;\n  border-radius: 30px;\n}\n", ""]);

// exports


/***/ }),
/* 340 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _regenerator = __webpack_require__(0);

var _regenerator2 = _interopRequireDefault(_regenerator);

var _assign = __webpack_require__(4);

var _assign2 = _interopRequireDefault(_assign);

var _asyncToGenerator2 = __webpack_require__(1);

var _asyncToGenerator3 = _interopRequireDefault(_asyncToGenerator2);

var _utils = __webpack_require__(2);

var _api = __webpack_require__(8);

var _api2 = _interopRequireDefault(_api);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

/**
 * the response likes below:
 * {
"activityParticipationId": 0,
"id": 0,
"punchDateTime": "2018-06-19T16:24:33.403Z",
"signNote": "string",
"title": "string"
}
 *
 *
 */
exports.default = function () {
  var _ref = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee() {
    var params = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : {};

    var url, _params, res;

    return _regenerator2.default.wrap(function _callee$(_context) {
      while (1) {
        switch (_context.prev = _context.next) {
          case 0:
            /**
             * 
            params = {
            "activityParticipationId": 0,
            "id": 0,
            "punchDateTime": "2018-06-19T16:24:33.406Z",
            "signNote": "string",
            "title": "string"
            }
            **/
            url = _api2.default.createClockIn.url;
            _params = (0, _assign2.default)({}, _api2.default.createClockIn, {
              url: url,
              body: params
            });
            _context.next = 4;
            return (0, _utils.fetchAPI)(_params);

          case 4:
            res = _context.sent;


            console.log('createClockIn response: ', res);

            return _context.abrupt('return', res);

          case 7:
          case 'end':
            return _context.stop();
        }
      }
    }, _callee, this);
  }));

  function createClockIn() {
    return _ref.apply(this, arguments);
  }

  return createClockIn;
}();

/***/ }),
/* 341 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return render; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "b", function() { return staticRenderFns; });
var render = function() {
  var _vm = this
  var _h = _vm.$createElement
  var _c = _vm._self._c || _h
  return _c("div", { staticClass: "check" }, [
    _vm.activity.images
      ? _c("img", {
          staticClass: "check-img",
          attrs: { src: _vm.activity.images[0].ossPath, alt: "" }
        })
      : _vm._e(),
    _vm._v(" "),
    _c("div", { staticClass: "info-container" }, [
      _c("p", { staticClass: "fs18" }, [_vm._v(_vm._s(_vm.activity.title))]),
      _vm._v(" "),
      _c("p", { staticClass: "info" }, [
        _vm._v(_vm._s(_vm.activity.descrption))
      ])
    ]),
    _vm._v(" "),
    _c("div", { staticClass: "diary-container" }, [
      _c("p", { staticClass: "fs14 black" }, [_vm._v("请输入签到日记")]),
      _vm._v(" "),
      _c("textarea", {
        directives: [
          {
            name: "model",
            rawName: "v-model",
            value: _vm.signNote,
            expression: "signNote"
          }
        ],
        staticClass: "diary",
        attrs: { rows: "6", placeholder: "请输入你的签到日记吧" },
        domProps: { value: _vm.signNote },
        on: {
          input: function($event) {
            if ($event.target.composing) {
              return
            }
            _vm.signNote = $event.target.value
          }
        }
      })
    ]),
    _vm._v(" "),
    _c(
      "div",
      { staticClass: "upload-container" },
      [
        _c("upload", {
          ref: "upload",
          attrs: { finishUpload: _vm.finishUpload }
        })
      ],
      1
    ),
    _vm._v(" "),
    _c(
      "div",
      { staticClass: "button-container" },
      [
        _c(
          "Button",
          {
            staticClass: "check-button fs16 black",
            attrs: { long: "" },
            on: { click: _vm.submit }
          },
          [_vm._v("打卡")]
        )
      ],
      1
    )
  ])
}
var staticRenderFns = []
render._withStripped = true

if (false) {
  module.hot.accept()
  if (module.hot.data) {
    require("vue-hot-reload-api")      .rerender("data-v-96f0dff0", { render: render, staticRenderFns: staticRenderFns })
  }
}

/***/ }),
/* 342 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue__ = __webpack_require__(129);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue__);
/* harmony namespace reexport (unknown) */ for(var __WEBPACK_IMPORT_KEY__ in __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue__) if(__WEBPACK_IMPORT_KEY__ !== 'default') (function(key) { __webpack_require__.d(__webpack_exports__, key, function() { return __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue__[key]; }) }(__WEBPACK_IMPORT_KEY__));
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_vue_loader_lib_template_compiler_index_id_data_v_21046757_hasScoped_true_optionsId_0_buble_transforms_node_modules_vue_loader_lib_selector_type_template_index_0_index_vue__ = __webpack_require__(348);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__node_modules_vue_loader_lib_runtime_component_normalizer__ = __webpack_require__(6);
var disposed = false
function injectStyle (context) {
  if (disposed) return
  __webpack_require__(343)
  __webpack_require__(345)
}
/* script */


/* template */

/* template functional */
var __vue_template_functional__ = false
/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-21046757"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null

var Component = Object(__WEBPACK_IMPORTED_MODULE_2__node_modules_vue_loader_lib_runtime_component_normalizer__["a" /* default */])(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue___default.a,
  __WEBPACK_IMPORTED_MODULE_1__node_modules_vue_loader_lib_template_compiler_index_id_data_v_21046757_hasScoped_true_optionsId_0_buble_transforms_node_modules_vue_loader_lib_selector_type_template_index_0_index_vue__["a" /* render */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_vue_loader_lib_template_compiler_index_id_data_v_21046757_hasScoped_true_optionsId_0_buble_transforms_node_modules_vue_loader_lib_selector_type_template_index_0_index_vue__["b" /* staticRenderFns */],
  __vue_template_functional__,
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "dev\\pages\\fit\\views\\user-center\\index.vue"

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-21046757", Component.options)
  } else {
    hotAPI.reload("data-v-21046757", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["default"] = (Component.exports);


/***/ }),
/* 343 */
/***/ (function(module, exports, __webpack_require__) {


var content = __webpack_require__(344);

if(typeof content === 'string') content = [[module.i, content, '']];

var transform;
var insertInto;



var options = {"hmr":true}

options.transform = transform
options.insertInto = undefined;

var update = __webpack_require__(5)(content, options);

if(content.locals) module.exports = content.locals;

if(false) {
	module.hot.accept("!!../../../../../node_modules/css-loader/index.js!../../../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"optionsId\":\"0\",\"vue\":true,\"id\":\"data-v-21046757\",\"scoped\":true,\"sourceMap\":false}!../../../../../node_modules/postcss-loader/lib/index.js!../../../../../node_modules/less-loader/dist/cjs.js!../../../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./index.vue", function() {
		var newContent = require("!!../../../../../node_modules/css-loader/index.js!../../../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"optionsId\":\"0\",\"vue\":true,\"id\":\"data-v-21046757\",\"scoped\":true,\"sourceMap\":false}!../../../../../node_modules/postcss-loader/lib/index.js!../../../../../node_modules/less-loader/dist/cjs.js!../../../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./index.vue");

		if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];

		var locals = (function(a, b) {
			var key, idx = 0;

			for(key in a) {
				if(!b || a[key] !== b[key]) return false;
				idx++;
			}

			for(key in b) idx--;

			return idx === 0;
		}(content.locals, newContent.locals));

		if(!locals) throw new Error('Aborting CSS HMR due to changed css-modules locals.');

		update(newContent);
	});

	module.hot.dispose(function() { update(); });
}

/***/ }),
/* 344 */
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(3)(false);
// imports


// module
exports.push([module.i, "\n.personal .section[data-v-21046757] {\n  border-top: 10px solid #f6f6f6;\n  background: #fff;\n}\n.personal .section .tab[data-v-21046757] {\n  display: -moz-box;\n  display: flex;\n  -moz-box-align: center;\n       align-items: center;\n  border-bottom: 1px solid #E2E2E2;\n  text-align: center;\n}\n.personal .section .tab h3[data-v-21046757] {\n  font-size: 14px;\n  font-weight: 400;\n  width: 50%;\n  line-height: 45px;\n}\n.personal .section .tab h3[data-v-21046757]:nth-child(1) {\n  border-right: 1px solid #E2E2E2;\n}\n.personal .section .tab h3[data-v-21046757]:nth-child(2) {\n  border-right: 1px solid #E2E2E2;\n}\n.personal .section .tab .yellow[data-v-21046757] {\n  color: #FFD52F;\n}\n.personal .section .content[data-v-21046757] {\n  height: 99px;\n}\n.personal .section .content .w346[data-v-21046757] {\n  width: 346px;\n  margin: 0 auto;\n}\n.personal .section .content .time-title[data-v-21046757] {\n  margin-top: 6px;\n}\n.personal .section .content .line[data-v-21046757] {\n  width: 100%;\n  height: 2px;\n  background-color: #F1F1F1;\n}\n.personal .section .content .title[data-v-21046757] {\n  font-size: 16px;\n  color: #000;\n  margin-top: 9px;\n  position: relative;\n}\n.personal .section .content .title .activityPop[data-v-21046757] {\n  position: absolute;\n  top: 26px;\n  right: -8px;\n}\n.personal .section .content .title .activityPop li[data-v-21046757] {\n  width: 120px;\n  height: 30px;\n  font-size: 12px;\n  color: #fff;\n  background-color: rgba(0, 0, 0, 0.8);\n  text-align: center;\n  line-height: 30px;\n  border-radius: 2px;\n  -webkit-border-radius: 2px;\n}\n.personal .section .content .desc[data-v-21046757] {\n  margin-top: 10px;\n  margin-bottom: 10px;\n  height: 20px;\n}\n.personal .section .content .headPicture[data-v-21046757] {\n  width: 20px;\n  height: 20px;\n  float: left;\n  line-height: 20px;\n}\n.personal .section .content .headPicture img[data-v-21046757] {\n  width: auto;\n  height: 100%;\n  border-radius: 50%;\n  -webkit-border-radius: 50%;\n}\n.personal .section .content .rightIcon[data-v-21046757] {\n  float: right;\n}\n.personal .section .content .descInfo[data-v-21046757] {\n  float: left;\n  height: 18px;\n  line-height: 18px;\n  margin-top: 2px;\n  margin-left: 6px;\n  color: #959595;\n}\n.personal .section .content .descInfo .infoname[data-v-21046757] {\n  width: 70px;\n  float: left;\n  overflow: hidden;\n  text-overflow: ellipsis;\n  white-space: nowrap;\n}\n.personal .section .content .descInfo .status[data-v-21046757] {\n  float: left;\n  margin-left: 6px;\n}\n.personal .section .content .descInfo .green[data-v-21046757] {\n  color: #1fb56a;\n}\n.personal .section .content .descDetail[data-v-21046757] {\n  float: right;\n}\n.personal .section .content .descDetail .baoming[data-v-21046757] {\n  display: inline-block;\n}\n.personal .section .content .descDetail .viewDetail[data-v-21046757] {\n  display: inline-block;\n  margin-left: 8px;\n  color: #FFD52F;\n}\n.personal .info[data-v-21046757] {\n  background-color: #ffd52f;\n  height: 100px;\n  width: 100%;\n  display: -moz-box;\n  display: flex;\n  padding: 0 6.5%;\n  -moz-box-align: center;\n       align-items: center;\n}\n.personal .info .photo[data-v-21046757] {\n  width: 50px;\n  height: 50px;\n  border-radius: 50%;\n  border: 2px solid #fff;\n  background-repeat: no-repeat;\n  background-position: center center;\n  background-size: contain;\n}\n.personal .info .detail[data-v-21046757] {\n  padding-left: 15px;\n  -moz-box-flex: 1;\n       flex-grow: 1;\n}\n.personal .info .follow-btn[data-v-21046757] {\n  width: 77px;\n  height: 26px;\n  color: #000;\n  background-color: #fff;\n  border-radius: 20px;\n  border: none;\n}\n.personal .record-time[data-v-21046757] {\n  /*margin-top:-10px;*/\n  font-size: 12px;\n  color: #9b9b9b;\n}\n", ""]);

// exports


/***/ }),
/* 345 */
/***/ (function(module, exports, __webpack_require__) {


var content = __webpack_require__(346);

if(typeof content === 'string') content = [[module.i, content, '']];

var transform;
var insertInto;



var options = {"hmr":true}

options.transform = transform
options.insertInto = undefined;

var update = __webpack_require__(5)(content, options);

if(content.locals) module.exports = content.locals;

if(false) {
	module.hot.accept("!!../../../../../node_modules/css-loader/index.js!../../../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"optionsId\":\"0\",\"vue\":true,\"scoped\":false,\"sourceMap\":false}!../../../../../node_modules/postcss-loader/lib/index.js!../../../../../node_modules/less-loader/dist/cjs.js!../../../../../node_modules/vue-loader/lib/selector.js?type=styles&index=1!./index.vue", function() {
		var newContent = require("!!../../../../../node_modules/css-loader/index.js!../../../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"optionsId\":\"0\",\"vue\":true,\"scoped\":false,\"sourceMap\":false}!../../../../../node_modules/postcss-loader/lib/index.js!../../../../../node_modules/less-loader/dist/cjs.js!../../../../../node_modules/vue-loader/lib/selector.js?type=styles&index=1!./index.vue");

		if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];

		var locals = (function(a, b) {
			var key, idx = 0;

			for(key in a) {
				if(!b || a[key] !== b[key]) return false;
				idx++;
			}

			for(key in b) idx--;

			return idx === 0;
		}(content.locals, newContent.locals));

		if(!locals) throw new Error('Aborting CSS HMR due to changed css-modules locals.');

		update(newContent);
	});

	module.hot.dispose(function() { update(); });
}

/***/ }),
/* 346 */
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(3)(false);
// imports


// module
exports.push([module.i, "\n.personal .ivu-date-picker-rel {\n  display: none !important;\n}\n.personal .ivu-select-dropdown {\n  position: unset !important;\n}\n.personal .ivu-picker-panel-body {\n  width: 100%;\n}\n.personal .ivu-date-picker-cells {\n  width: 100%;\n  margin: 0;\n}\n.personal .ivu-date-picker-cells-cell {\n  width: 14%;\n  height: 30px;\n  text-align: center;\n}\n.personal .ivu-date-picker-cells-header {\n  width: 100%;\n}\n.personal .ivu-date-picker-cells-header span {\n  width: 12.8%;\n  display: inline-block;\n}\n.personal .ivu-date-picker-header {\n  border-bottom: 1px solid #E2E2E2;\n}\n", ""]);

// exports


/***/ }),
/* 347 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _regenerator = __webpack_require__(0);

var _regenerator2 = _interopRequireDefault(_regenerator);

var _assign = __webpack_require__(4);

var _assign2 = _interopRequireDefault(_assign);

var _asyncToGenerator2 = __webpack_require__(1);

var _asyncToGenerator3 = _interopRequireDefault(_asyncToGenerator2);

var _utils = __webpack_require__(2);

var _api = __webpack_require__(8);

var _api2 = _interopRequireDefault(_api);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

// [
//   {
//     "activityParticipationId": 0,
//     "id": 0,
//     "pics": [
//       {
//         "clockInId": 0,
//         "createTime": "string",
//         "fitnessActivityId": 0,
//         "id": 0,
//         "ossPath": "string"
//       }
//     ],
//     "punchDateTime": "string",
//     "signNote": "string",
//     "title": "string",
//     "wechatUserId": 0
//   }
// ]

exports.default = function () {
  var _ref = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee2() {
    var params = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : {
      wechatUserId: wechatUserId,
      yearMonthDate: yearMonthDate
    };
    return _regenerator2.default.wrap(function _callee2$(_context2) {
      while (1) {
        switch (_context2.prev = _context2.next) {
          case 0:
            return _context2.abrupt('return', (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee() {
              var wechatUserId, yearMonthDate, _search, url, _params, res;

              return _regenerator2.default.wrap(function _callee$(_context) {
                while (1) {
                  switch (_context.prev = _context.next) {
                    case 0:
                      wechatUserId = params.wechatUserId, yearMonthDate = params.yearMonthDate;

                      if (!(!wechatUserId && !yearMonthDate)) {
                        _context.next = 3;
                        break;
                      }

                      return _context.abrupt('return');

                    case 3:
                      _search = (0, _utils.joinParams)(params);
                      url = _api2.default.getClockinsByWechatUserIdAndDate.url + '/?' + _search;
                      _params = (0, _assign2.default)({}, _api2.default.getClockinsByWechatUserIdAndDate, {
                        url: url
                      });
                      _context.next = 8;
                      return (0, _utils.fetchAPI)(_params);

                    case 8:
                      res = _context.sent;


                      console.log('getClockinsByWechatUserIdAndDate response: ', res);

                      return _context.abrupt('return', res);

                    case 11:
                    case 'end':
                      return _context.stop();
                  }
                }
              }, _callee, this);
            }))());

          case 1:
          case 'end':
            return _context2.stop();
        }
      }
    }, _callee2, this);
  }));

  function getClockinsByWechatUserIdAndDate() {
    return _ref.apply(this, arguments);
  }

  return getClockinsByWechatUserIdAndDate;
}();

/***/ }),
/* 348 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return render; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "b", function() { return staticRenderFns; });
var render = function() {
  var _vm = this
  var _h = _vm.$createElement
  var _c = _vm._self._c || _h
  return _c("div", { staticClass: "personal" }, [
    _c("div", { staticClass: "info" }, [
      _c("div", {
        staticClass: "photo",
        style: { backgroundImage: "url(" + _vm.userInfo.avatar + ")" }
      }),
      _vm._v(" "),
      _c("div", { staticClass: "detail" }, [
        _c("h3", { staticClass: "f218" }, [
          _vm._v(_vm._s(_vm.userInfo.nickName))
        ])
      ])
    ]),
    _vm._v(" "),
    _c("div", { staticClass: "section" }, [
      _c("div", { staticClass: "tab" }, [
        _c(
          "h3",
          {
            class: _vm.isMyPublish === 0 ? "yellow" : null,
            on: {
              click: function($event) {
                _vm.isMyPublish = 0
              }
            }
          },
          [_vm._v("时间轴")]
        ),
        _vm._v(" "),
        _c(
          "h3",
          {
            class: _vm.isMyPublish === 1 ? "yellow" : null,
            on: {
              click: function($event) {
                _vm.isMyPublish = 1
              }
            }
          },
          [_vm._v("我参与的")]
        ),
        _vm._v(" "),
        _c(
          "h3",
          {
            class: _vm.isMyPublish === 2 ? "yellow" : null,
            on: {
              click: function($event) {
                _vm.isMyPublish = 2
              }
            }
          },
          [_vm._v("我发布的")]
        )
      ]),
      _vm._v(" "),
      _vm.isMyPublish === 0
        ? _c("div", { staticClass: "timeline" }, [
            _c("div", { staticClass: "content" }, [
              _c(
                "div",
                { staticClass: "time w346" },
                [
                  _c("Icon", { attrs: { type: "calendar" } }),
                  _vm._v("\n          07-05\n        ")
                ],
                1
              )
            ])
          ])
        : _vm._e(),
      _vm._v(" "),
      _vm.isMyPublish === 1
        ? _c(
            "div",
            { staticClass: "joined" },
            _vm._l(_vm.lists, function(list, index) {
              return _c(
                "div",
                {
                  key: index,
                  staticClass: "content",
                  on: {
                    click: function($event) {
                      _vm.gotoDetail(list.id)
                    }
                  }
                },
                [
                  _c(
                    "div",
                    { staticClass: "time-title w346" },
                    [
                      _c("Icon", {
                        staticClass: "mr10 fs12",
                        attrs: { type: "clock" }
                      }),
                      _vm._v(
                        "\n          " +
                          _vm._s(list.activityStartTime) +
                          "\n        "
                      )
                    ],
                    1
                  ),
                  _vm._v(" "),
                  _c("div", { staticClass: "title w346" }, [
                    _vm._v("\n          " + _vm._s(list.title) + "\n        ")
                  ]),
                  _vm._v(" "),
                  _c("div", { staticClass: "desc w346" }, [
                    _c("div", { staticClass: "headPicture" }, [
                      _c("img", { attrs: { src: list.avatar, alt: "头像" } })
                    ]),
                    _vm._v(" "),
                    _c("div", { staticClass: "descInfo" }, [
                      _c("span", { staticClass: "infoname" }, [
                        _vm._v(_vm._s(list.nickName))
                      ]),
                      _vm._v(" "),
                      _c(
                        "span",
                        {
                          staticClass: "status",
                          class: {
                            green: list.status === 1,
                            null: list.status === 2
                          }
                        },
                        [_vm._v(_vm._s(_vm.activityStatus[list.status]))]
                      )
                    ]),
                    _vm._v(" "),
                    _c("div", { staticClass: "descDetail" }, [
                      _c("div", { staticClass: "baoming" }, [
                        _vm._v(_vm._s(list.attendCount) + "人报名")
                      ]),
                      _vm._v(" "),
                      _c("div", { staticClass: "viewDetail" }, [
                        _vm._v("查看详情")
                      ])
                    ])
                  ]),
                  _vm._v(" "),
                  _c("div", { staticClass: "line" })
                ]
              )
            })
          )
        : _vm._e(),
      _vm._v(" "),
      _vm.isMyPublish === 2
        ? _c(
            "div",
            { staticClass: "published" },
            _vm._l(_vm.lists, function(list, index) {
              return _c("div", { key: index, staticClass: "content" }, [
                _c(
                  "div",
                  { staticClass: "time-title w346" },
                  [
                    _c("Icon", {
                      staticClass: "mr10 fs12",
                      attrs: { type: "clock" }
                    }),
                    _vm._v(
                      "\n          " +
                        _vm._s(list.activityStartTime) +
                        "\n        "
                    )
                  ],
                  1
                ),
                _vm._v(" "),
                _c(
                  "div",
                  { staticClass: "title w346" },
                  [
                    _vm._v(
                      "\n          " + _vm._s(list.title) + "\n          "
                    ),
                    _c("Icon", {
                      staticClass: "rightIcon",
                      attrs: { size: "24", type: "android-more-horizontal" },
                      on: {
                        click: function($event) {
                          _vm.showPop(index)
                        }
                      }
                    }),
                    _vm._v(" "),
                    _c(
                      "div",
                      {
                        directives: [
                          {
                            name: "show",
                            rawName: "v-show",
                            value: list.isShowPop,
                            expression: "list.isShowPop"
                          }
                        ],
                        staticClass: "activityPop"
                      },
                      [_vm._m(0, true)]
                    )
                  ],
                  1
                ),
                _vm._v(" "),
                _c("div", { staticClass: "desc w346" }, [
                  _c("div", { staticClass: "headPicture" }, [
                    _c("img", { attrs: { src: list.avatar, alt: "头像" } })
                  ]),
                  _vm._v(" "),
                  _c("div", { staticClass: "descInfo" }, [
                    _c("span", { staticClass: "infoname" }, [
                      _vm._v(_vm._s(list.nickName))
                    ]),
                    _vm._v(" "),
                    _c(
                      "span",
                      {
                        staticClass: "status",
                        class: {
                          green: list.status === 1,
                          null: list.status === 2
                        }
                      },
                      [_vm._v(_vm._s(_vm.activityStatus[list.status]))]
                    )
                  ]),
                  _vm._v(" "),
                  _c("div", { staticClass: "descDetail" }, [
                    _c("div", { staticClass: "baoming" }, [
                      _vm._v(_vm._s(list.attendCount) + "人报名")
                    ]),
                    _vm._v(" "),
                    _c("div", { staticClass: "viewDetail" }, [
                      _vm._v("查看详情")
                    ])
                  ])
                ]),
                _vm._v(" "),
                _c("div", { staticClass: "line" })
              ])
            })
          )
        : _vm._e()
    ])
  ])
}
var staticRenderFns = [
  function() {
    var _vm = this
    var _h = _vm.$createElement
    var _c = _vm._self._c || _h
    return _c("ul", [_c("li", [_vm._v("编辑活动")])])
  }
]
render._withStripped = true

if (false) {
  module.hot.accept()
  if (module.hot.data) {
    require("vue-hot-reload-api")      .rerender("data-v-21046757", { render: render, staticRenderFns: staticRenderFns })
  }
}

/***/ }),
/* 349 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue__ = __webpack_require__(130);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue__);
/* harmony namespace reexport (unknown) */ for(var __WEBPACK_IMPORT_KEY__ in __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue__) if(__WEBPACK_IMPORT_KEY__ !== 'default') (function(key) { __webpack_require__.d(__webpack_exports__, key, function() { return __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue__[key]; }) }(__WEBPACK_IMPORT_KEY__));
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_vue_loader_lib_template_compiler_index_id_data_v_83aeb126_hasScoped_true_optionsId_0_buble_transforms_node_modules_vue_loader_lib_selector_type_template_index_0_index_vue__ = __webpack_require__(356);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__node_modules_vue_loader_lib_runtime_component_normalizer__ = __webpack_require__(6);
var disposed = false
function injectStyle (context) {
  if (disposed) return
  __webpack_require__(350)
  __webpack_require__(352)
}
/* script */


/* template */

/* template functional */
var __vue_template_functional__ = false
/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-83aeb126"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null

var Component = Object(__WEBPACK_IMPORTED_MODULE_2__node_modules_vue_loader_lib_runtime_component_normalizer__["a" /* default */])(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue___default.a,
  __WEBPACK_IMPORTED_MODULE_1__node_modules_vue_loader_lib_template_compiler_index_id_data_v_83aeb126_hasScoped_true_optionsId_0_buble_transforms_node_modules_vue_loader_lib_selector_type_template_index_0_index_vue__["a" /* render */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_vue_loader_lib_template_compiler_index_id_data_v_83aeb126_hasScoped_true_optionsId_0_buble_transforms_node_modules_vue_loader_lib_selector_type_template_index_0_index_vue__["b" /* staticRenderFns */],
  __vue_template_functional__,
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "dev\\pages\\fit\\views\\record\\index.vue"

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-83aeb126", Component.options)
  } else {
    hotAPI.reload("data-v-83aeb126", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["default"] = (Component.exports);


/***/ }),
/* 350 */
/***/ (function(module, exports, __webpack_require__) {


var content = __webpack_require__(351);

if(typeof content === 'string') content = [[module.i, content, '']];

var transform;
var insertInto;



var options = {"hmr":true}

options.transform = transform
options.insertInto = undefined;

var update = __webpack_require__(5)(content, options);

if(content.locals) module.exports = content.locals;

if(false) {
	module.hot.accept("!!../../../../../node_modules/css-loader/index.js!../../../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"optionsId\":\"0\",\"vue\":true,\"id\":\"data-v-83aeb126\",\"scoped\":true,\"sourceMap\":false}!../../../../../node_modules/postcss-loader/lib/index.js!../../../../../node_modules/less-loader/dist/cjs.js!../../../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./index.vue", function() {
		var newContent = require("!!../../../../../node_modules/css-loader/index.js!../../../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"optionsId\":\"0\",\"vue\":true,\"id\":\"data-v-83aeb126\",\"scoped\":true,\"sourceMap\":false}!../../../../../node_modules/postcss-loader/lib/index.js!../../../../../node_modules/less-loader/dist/cjs.js!../../../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./index.vue");

		if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];

		var locals = (function(a, b) {
			var key, idx = 0;

			for(key in a) {
				if(!b || a[key] !== b[key]) return false;
				idx++;
			}

			for(key in b) idx--;

			return idx === 0;
		}(content.locals, newContent.locals));

		if(!locals) throw new Error('Aborting CSS HMR due to changed css-modules locals.');

		update(newContent);
	});

	module.hot.dispose(function() { update(); });
}

/***/ }),
/* 351 */
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(3)(false);
// imports


// module
exports.push([module.i, "\n.fit[data-v-83aeb126] {\n  overflow: auto;\n}\n.top-container[data-v-83aeb126] {\n  width: 100%;\n  background: #FFD52F;\n  text-align: center;\n  padding-top: 0.4rem;\n}\n.avatar[data-v-83aeb126] {\n  width: 0.6rem;\n  height: 0.6rem;\n  border-radius: 0.6rem;\n}\n.name[data-v-83aeb126] {\n  font-size: 20px;\n  padding-top: 0.05rem;\n}\n.switch-container[data-v-83aeb126] {\n  color: #666666;\n  padding-top: 0.15rem;\n  padding-bottom: 0.15rem;\n}\n.switch-container .switch-item[data-v-83aeb126] {\n  border-right: 1px solid #fff03c;\n}\n.switch-container .num[data-v-83aeb126] {\n  font-size: 24px;\n  color: black;\n}\n.switch-item[data-v-83aeb126]:nth-child(even) {\n  border: none;\n}\n.timeline-container[data-v-83aeb126] {\n  padding: 0.3rem;\n  background: white;\n}\n.timeline-container .time-item[data-v-83aeb126] {\n  padding-left: 0.15rem;\n}\n.timeline-container .timeline-item[data-v-83aeb126] {\n  border-bottom: 1px solid #E2E2E2;\n}\n.timeline-container .item-title[data-v-83aeb126] {\n  font-size: 16px;\n  color: #4A4A4A;\n  font-weight: 500;\n}\n.timeline-container .item-content[data-v-83aeb126] {\n  font-size: 12px;\n  color: #9B9B9B;\n  padding-bottom: 0.1rem;\n}\n.timeline-container .item-time[data-v-83aeb126] {\n  font-size: 12px;\n  color: #9B9B9B;\n}\n.timeline-container .item-pics .img[data-v-83aeb126] {\n  display: inline-block;\n  width: 0.7rem;\n  height: 0.7rem;\n  background-repeat: no-repeat;\n  background-size: contain;\n  background-position: center center;\n  background-color: #fff;\n  margin-right: 0.1rem;\n  margin-bottom: 0.1rem;\n}\n.time-icon[data-v-83aeb126] {\n  font-size: 26px;\n  color: black;\n}\n.time-date[data-v-83aeb126] {\n  color: black;\n}\n.no-activity[data-v-83aeb126] {\n  text-align: center;\n}\n", ""]);

// exports


/***/ }),
/* 352 */
/***/ (function(module, exports, __webpack_require__) {

// style-loader: Adds some css to the DOM by adding a <style> tag

// load the styles
var content = __webpack_require__(353);
if(typeof content === 'string') content = [[module.i, content, '']];
if(content.locals) module.exports = content.locals;
// add the styles to the DOM
var add = __webpack_require__(16).default
var update = add("83dbb000", content, false, {});
// Hot Module Replacement
if(false) {
 // When the styles change, update the <style> tags
 if(!content.locals) {
   module.hot.accept("!!../../../../../node_modules/css-loader/index.js!../../../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"optionsId\":\"0\",\"vue\":true,\"scoped\":false,\"sourceMap\":false}!../../../../../node_modules/vue-loader/lib/selector.js?type=styles&index=1!./index.vue", function() {
     var newContent = require("!!../../../../../node_modules/css-loader/index.js!../../../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"optionsId\":\"0\",\"vue\":true,\"scoped\":false,\"sourceMap\":false}!../../../../../node_modules/vue-loader/lib/selector.js?type=styles&index=1!./index.vue");
     if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];
     update(newContent);
   });
 }
 // When the module is disposed, remove the <style> tags
 module.hot.dispose(function() { update(); });
}

/***/ }),
/* 353 */
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(3)(false);
// imports


// module
exports.push([module.i, "\n.record .ivu-timeline-item-head {\n  background: #fff;\n  margin-top:20px;\n}\n", ""]);

// exports


/***/ }),
/* 354 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _regenerator = __webpack_require__(0);

var _regenerator2 = _interopRequireDefault(_regenerator);

var _assign = __webpack_require__(4);

var _assign2 = _interopRequireDefault(_assign);

var _asyncToGenerator2 = __webpack_require__(1);

var _asyncToGenerator3 = _interopRequireDefault(_asyncToGenerator2);

var _utils = __webpack_require__(2);

var _api = __webpack_require__(8);

var _api2 = _interopRequireDefault(_api);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

/*{  "activityId": 0,  "avatar": "string",  "clockinCount": 0,  "id": 0,  "nickName": "string",  "participationTime": "string",  "project": "string",  "wechatUserId": "string"}*/

exports.default = function () {
  var _ref = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee2() {
    var params = arguments.length > 0 && arguments[0] !== undefined ? arguments[0] : {
      activityParticipationId: activityParticipationId
    };
    return _regenerator2.default.wrap(function _callee2$(_context2) {
      while (1) {
        switch (_context2.prev = _context2.next) {
          case 0:
            return _context2.abrupt('return', (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee() {
              var activityParticipationId, _search, url, _params, res;

              return _regenerator2.default.wrap(function _callee$(_context) {
                while (1) {
                  switch (_context.prev = _context.next) {
                    case 0:
                      activityParticipationId = params.activityParticipationId;

                      if (activityParticipationId) {
                        _context.next = 3;
                        break;
                      }

                      return _context.abrupt('return');

                    case 3:
                      _search = (0, _utils.joinParams)(params);
                      url = _api2.default.getClockinsByActivityParticipationId.url + '/?' + _search;
                      _params = (0, _assign2.default)({}, _api2.default.getClockinsByActivityParticipationId, {
                        url: url
                      });
                      _context.next = 8;
                      return (0, _utils.fetchAPI)(_params);

                    case 8:
                      res = _context.sent;


                      console.log('getClockinsByActivityParticipationId response: ', res);

                      return _context.abrupt('return', res);

                    case 11:
                    case 'end':
                      return _context.stop();
                  }
                }
              }, _callee, this);
            }))());

          case 1:
          case 'end':
            return _context2.stop();
        }
      }
    }, _callee2, this);
  }));

  function getClockinsByActivityParticipationId() {
    return _ref.apply(this, arguments);
  }

  return getClockinsByActivityParticipationId;
}();

/***/ }),
/* 355 */
/***/ (function(module, exports, __webpack_require__) {

"use strict";


Object.defineProperty(exports, "__esModule", {
  value: true
});

var _regenerator = __webpack_require__(0);

var _regenerator2 = _interopRequireDefault(_regenerator);

var _assign = __webpack_require__(4);

var _assign2 = _interopRequireDefault(_assign);

var _asyncToGenerator2 = __webpack_require__(1);

var _asyncToGenerator3 = _interopRequireDefault(_asyncToGenerator2);

var _utils = __webpack_require__(2);

var _api = __webpack_require__(8);

var _api2 = _interopRequireDefault(_api);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

/**
 * the response likes below:
 * {
"avatar": "string",
"companyRole": "string",
"cookFlag": true,
"createTime": "2018-06-19T16:24:33.910Z",
"id": 0,
"intesting": "string",
"introduce": "string",
"mobileNum": "string",
"modifyTime": "2018-06-19T16:24:33.910Z",
"nickName": "string",
"openId": "string",
"project": "string",
"seat": "string",
"sex": true,
"skill": "string",
"userName": "string",
"wechatCode": "string"
}
 *
 *
 */
exports.default = function () {
  var _ref = (0, _asyncToGenerator3.default)( /*#__PURE__*/_regenerator2.default.mark(function _callee(id) {
    var url, _params, res;

    return _regenerator2.default.wrap(function _callee$(_context) {
      while (1) {
        switch (_context.prev = _context.next) {
          case 0:
            url = _api2.default.getWechatUser.url.replace('{{id}}', id);
            _params = (0, _assign2.default)({}, _api2.default.getWechatUser, {
              url: url
            });
            _context.next = 4;
            return (0, _utils.fetchAPI)(_params);

          case 4:
            res = _context.sent;


            console.log('getWechatUser response: ', res);

            return _context.abrupt('return', res);

          case 7:
          case 'end':
            return _context.stop();
        }
      }
    }, _callee, this);
  }));

  function getWechatUser(_x) {
    return _ref.apply(this, arguments);
  }

  return getWechatUser;
}();

/***/ }),
/* 356 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return render; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "b", function() { return staticRenderFns; });
var render = function() {
  var _vm = this
  var _h = _vm.$createElement
  var _c = _vm._self._c || _h
  return _c("div", { staticClass: "record" }, [
    _c(
      "div",
      { staticClass: "top-container" },
      [
        _c("img", {
          staticClass: "avatar",
          attrs: { src: _vm.userInfo.avatar, alt: "" }
        }),
        _vm._v(" "),
        _c("p", { staticClass: "name black" }, [
          _vm._v(_vm._s(_vm._f("decodeBase64")(_vm.userInfo.nickName)))
        ]),
        _vm._v(" "),
        _c(
          "Row",
          { staticClass: "switch-container" },
          [
            _c("Col", { staticClass: "switch-item", attrs: { span: "12" } }, [
              _c("p", { staticClass: "num" }, [_vm._v(_vm._s(_vm.rate))]),
              _vm._v(" "),
              _c("p", { staticClass: "black" }, [_vm._v("当前排名")])
            ]),
            _vm._v(" "),
            _c("Col", { staticClass: "switch-item", attrs: { span: "12" } }, [
              _c("p", { staticClass: "num" }, [
                _vm._v(_vm._s(_vm.record.length))
              ]),
              _vm._v(" "),
              _c("p", { staticClass: "black" }, [_vm._v("累计打卡")])
            ])
          ],
          1
        )
      ],
      1
    ),
    _vm._v(" "),
    _c(
      "div",
      { staticClass: "timeline-container" },
      [
        _vm.record.length
          ? _c(
              "Timeline",
              _vm._l(_vm.record, function(item, index) {
                return _c(
                  "TimelineItem",
                  { key: index, staticClass: "time-item" },
                  [
                    _c("Icon", {
                      staticClass: "time-icon",
                      attrs: { slot: "dot", type: "calendar" },
                      slot: "dot"
                    }),
                    _vm._v(" "),
                    _c(
                      "p",
                      {
                        staticClass: "time-date",
                        attrs: { slot: "dot" },
                        slot: "dot"
                      },
                      [_vm._v(_vm._s(_vm.onlyFormatDate(item.punchDateTime)))]
                    ),
                    _vm._v(" "),
                    _c("div", { staticClass: "timeline-item" }, [
                      _c("p", { staticClass: "item-title" }, [
                        _vm._v(_vm._s(item.signNote))
                      ]),
                      _vm._v(" "),
                      _c("p", { staticClass: "item-content" }, [
                        _vm._v(_vm._s(item.title))
                      ]),
                      _vm._v(" "),
                      item.pics
                        ? _c(
                            "div",
                            { staticClass: "item-pics" },
                            _vm._l(item.pics, function(pic, k) {
                              return _c("div", {
                                key: k,
                                staticClass: "img",
                                style:
                                  "background-image: url(" + pic.ossPath + ");",
                                on: {
                                  click: function($event) {
                                    _vm.openClickPreview(item.pics, k)
                                  }
                                }
                              })
                            })
                          )
                        : _vm._e(),
                      _vm._v(" "),
                      _c("p", { staticClass: "item-time" }, [
                        _vm._v(_vm._s(item.punchDateTime))
                      ])
                    ])
                  ],
                  1
                )
              })
            )
          : _vm._e(),
        _vm._v(" "),
        !_vm.record.length
          ? _c("p", { staticClass: "no-activity" }, [_vm._v("暂未参加活动")])
          : _vm._e()
      ],
      1
    )
  ])
}
var staticRenderFns = []
render._withStripped = true

if (false) {
  module.hot.accept()
  if (module.hot.data) {
    require("vue-hot-reload-api")      .rerender("data-v-83aeb126", { render: render, staticRenderFns: staticRenderFns })
  }
}

/***/ }),
/* 357 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue__ = __webpack_require__(131);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue__);
/* harmony namespace reexport (unknown) */ for(var __WEBPACK_IMPORT_KEY__ in __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue__) if(__WEBPACK_IMPORT_KEY__ !== 'default') (function(key) { __webpack_require__.d(__webpack_exports__, key, function() { return __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue__[key]; }) }(__WEBPACK_IMPORT_KEY__));
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_vue_loader_lib_template_compiler_index_id_data_v_c3058fce_hasScoped_true_optionsId_0_buble_transforms_node_modules_vue_loader_lib_selector_type_template_index_0_index_vue__ = __webpack_require__(360);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__node_modules_vue_loader_lib_runtime_component_normalizer__ = __webpack_require__(6);
var disposed = false
function injectStyle (context) {
  if (disposed) return
  __webpack_require__(358)
}
/* script */


/* template */

/* template functional */
var __vue_template_functional__ = false
/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-c3058fce"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null

var Component = Object(__WEBPACK_IMPORTED_MODULE_2__node_modules_vue_loader_lib_runtime_component_normalizer__["a" /* default */])(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue___default.a,
  __WEBPACK_IMPORTED_MODULE_1__node_modules_vue_loader_lib_template_compiler_index_id_data_v_c3058fce_hasScoped_true_optionsId_0_buble_transforms_node_modules_vue_loader_lib_selector_type_template_index_0_index_vue__["a" /* render */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_vue_loader_lib_template_compiler_index_id_data_v_c3058fce_hasScoped_true_optionsId_0_buble_transforms_node_modules_vue_loader_lib_selector_type_template_index_0_index_vue__["b" /* staticRenderFns */],
  __vue_template_functional__,
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "dev\\pages\\fit\\views\\popular\\index.vue"

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-c3058fce", Component.options)
  } else {
    hotAPI.reload("data-v-c3058fce", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["default"] = (Component.exports);


/***/ }),
/* 358 */
/***/ (function(module, exports, __webpack_require__) {


var content = __webpack_require__(359);

if(typeof content === 'string') content = [[module.i, content, '']];

var transform;
var insertInto;



var options = {"hmr":true}

options.transform = transform
options.insertInto = undefined;

var update = __webpack_require__(5)(content, options);

if(content.locals) module.exports = content.locals;

if(false) {
	module.hot.accept("!!../../../../../node_modules/css-loader/index.js!../../../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"optionsId\":\"0\",\"vue\":true,\"id\":\"data-v-c3058fce\",\"scoped\":true,\"sourceMap\":false}!../../../../../node_modules/postcss-loader/lib/index.js!../../../../../node_modules/less-loader/dist/cjs.js!../../../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./index.vue", function() {
		var newContent = require("!!../../../../../node_modules/css-loader/index.js!../../../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"optionsId\":\"0\",\"vue\":true,\"id\":\"data-v-c3058fce\",\"scoped\":true,\"sourceMap\":false}!../../../../../node_modules/postcss-loader/lib/index.js!../../../../../node_modules/less-loader/dist/cjs.js!../../../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./index.vue");

		if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];

		var locals = (function(a, b) {
			var key, idx = 0;

			for(key in a) {
				if(!b || a[key] !== b[key]) return false;
				idx++;
			}

			for(key in b) idx--;

			return idx === 0;
		}(content.locals, newContent.locals));

		if(!locals) throw new Error('Aborting CSS HMR due to changed css-modules locals.');

		update(newContent);
	});

	module.hot.dispose(function() { update(); });
}

/***/ }),
/* 359 */
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(3)(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/***/ }),
/* 360 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return render; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "b", function() { return staticRenderFns; });
var render = function() {
  var _vm = this
  var _h = _vm.$createElement
  var _c = _vm._self._c || _h
  return _c("popular-activity", { attrs: { activities: _vm.activities } })
}
var staticRenderFns = []
render._withStripped = true

if (false) {
  module.hot.accept()
  if (module.hot.data) {
    require("vue-hot-reload-api")      .rerender("data-v-c3058fce", { render: render, staticRenderFns: staticRenderFns })
  }
}

/***/ }),
/* 361 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue__ = __webpack_require__(132);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue__);
/* harmony namespace reexport (unknown) */ for(var __WEBPACK_IMPORT_KEY__ in __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue__) if(__WEBPACK_IMPORT_KEY__ !== 'default') (function(key) { __webpack_require__.d(__webpack_exports__, key, function() { return __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue__[key]; }) }(__WEBPACK_IMPORT_KEY__));
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__node_modules_vue_loader_lib_template_compiler_index_id_data_v_3b133c60_hasScoped_true_optionsId_0_buble_transforms_node_modules_vue_loader_lib_selector_type_template_index_0_index_vue__ = __webpack_require__(364);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__node_modules_vue_loader_lib_runtime_component_normalizer__ = __webpack_require__(6);
var disposed = false
function injectStyle (context) {
  if (disposed) return
  __webpack_require__(362)
}
/* script */


/* template */

/* template functional */
var __vue_template_functional__ = false
/* styles */
var __vue_styles__ = injectStyle
/* scopeId */
var __vue_scopeId__ = "data-v-3b133c60"
/* moduleIdentifier (server only) */
var __vue_module_identifier__ = null

var Component = Object(__WEBPACK_IMPORTED_MODULE_2__node_modules_vue_loader_lib_runtime_component_normalizer__["a" /* default */])(
  __WEBPACK_IMPORTED_MODULE_0__babel_loader_node_modules_vue_loader_lib_selector_type_script_index_0_index_vue___default.a,
  __WEBPACK_IMPORTED_MODULE_1__node_modules_vue_loader_lib_template_compiler_index_id_data_v_3b133c60_hasScoped_true_optionsId_0_buble_transforms_node_modules_vue_loader_lib_selector_type_template_index_0_index_vue__["a" /* render */],
  __WEBPACK_IMPORTED_MODULE_1__node_modules_vue_loader_lib_template_compiler_index_id_data_v_3b133c60_hasScoped_true_optionsId_0_buble_transforms_node_modules_vue_loader_lib_selector_type_template_index_0_index_vue__["b" /* staticRenderFns */],
  __vue_template_functional__,
  __vue_styles__,
  __vue_scopeId__,
  __vue_module_identifier__
)
Component.options.__file = "dev\\pages\\fit\\views\\discuss\\index.vue"

/* hot reload */
if (false) {(function () {
  var hotAPI = require("vue-hot-reload-api")
  hotAPI.install(require("vue"), false)
  if (!hotAPI.compatible) return
  module.hot.accept()
  if (!module.hot.data) {
    hotAPI.createRecord("data-v-3b133c60", Component.options)
  } else {
    hotAPI.reload("data-v-3b133c60", Component.options)
  }
  module.hot.dispose(function (data) {
    disposed = true
  })
})()}

/* harmony default export */ __webpack_exports__["default"] = (Component.exports);


/***/ }),
/* 362 */
/***/ (function(module, exports, __webpack_require__) {


var content = __webpack_require__(363);

if(typeof content === 'string') content = [[module.i, content, '']];

var transform;
var insertInto;



var options = {"hmr":true}

options.transform = transform
options.insertInto = undefined;

var update = __webpack_require__(5)(content, options);

if(content.locals) module.exports = content.locals;

if(false) {
	module.hot.accept("!!../../../../../node_modules/css-loader/index.js!../../../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"optionsId\":\"0\",\"vue\":true,\"id\":\"data-v-3b133c60\",\"scoped\":true,\"sourceMap\":false}!../../../../../node_modules/postcss-loader/lib/index.js!../../../../../node_modules/less-loader/dist/cjs.js!../../../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./index.vue", function() {
		var newContent = require("!!../../../../../node_modules/css-loader/index.js!../../../../../node_modules/vue-loader/lib/style-compiler/index.js?{\"optionsId\":\"0\",\"vue\":true,\"id\":\"data-v-3b133c60\",\"scoped\":true,\"sourceMap\":false}!../../../../../node_modules/postcss-loader/lib/index.js!../../../../../node_modules/less-loader/dist/cjs.js!../../../../../node_modules/vue-loader/lib/selector.js?type=styles&index=0!./index.vue");

		if(typeof newContent === 'string') newContent = [[module.id, newContent, '']];

		var locals = (function(a, b) {
			var key, idx = 0;

			for(key in a) {
				if(!b || a[key] !== b[key]) return false;
				idx++;
			}

			for(key in b) idx--;

			return idx === 0;
		}(content.locals, newContent.locals));

		if(!locals) throw new Error('Aborting CSS HMR due to changed css-modules locals.');

		update(newContent);
	});

	module.hot.dispose(function() { update(); });
}

/***/ }),
/* 363 */
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(3)(false);
// imports


// module
exports.push([module.i, "\n.discuss[data-v-3b133c60] {\n  background: #fff;\n  width: 100%;\n}\n.discuss .content[data-v-3b133c60] {\n  width: 100%;\n  height: 200px;\n  border: none;\n  padding: 10px;\n}\n.discuss .discuss-item[data-v-3b133c60] {\n  line-height: 0.5rem;\n  padding: 0 0.15rem;\n  display: -moz-box;\n  display: flex;\n  position: relative;\n}\n.discuss .btn[data-v-3b133c60] {\n  position: relative;\n  width: 93%;\n  height: 44px;\n  background: #FFD52F;\n  box-shadow: 0 0 2px 0 rgba(0, 0, 0, 0.2);\n  border-radius: 30px;\n  border: none;\n  font-size: 16px;\n  color: #000000;\n}\n.discuss .icon[data-v-3b133c60] {\n  position: absolute;\n  left: 39%;\n  top: 20%;\n}\n", ""]);

// exports


/***/ }),
/* 364 */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return render; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "b", function() { return staticRenderFns; });
var render = function() {
  var _vm = this
  var _h = _vm.$createElement
  var _c = _vm._self._c || _h
  return _c(
    "div",
    { staticClass: "discuss" },
    [
      _c("textarea", {
        directives: [
          {
            name: "model",
            rawName: "v-model",
            value: _vm.content,
            expression: "content"
          }
        ],
        staticClass: "content",
        attrs: { placeholder: "内容描述" },
        domProps: { value: _vm.content },
        on: {
          input: function($event) {
            if ($event.target.composing) {
              return
            }
            _vm.content = $event.target.value
          }
        }
      }),
      _vm._v(" "),
      _c(
        "div",
        { staticClass: "discuss-item" },
        [
          _c("Upload", {
            ref: "upload",
            attrs: { finishUpload: _vm.finishUpload }
          })
        ],
        1
      ),
      _vm._v(" "),
      _c(
        "div",
        { staticStyle: { "text-align": "center", padding: "0.15rem 0" } },
        [
          _c(
            "button",
            { staticClass: "btn", on: { click: _vm.submit } },
            [
              _c("Icon", {
                staticClass: "icon",
                attrs: { type: "ios-paperplane-outline", size: "30" }
              }),
              _vm._v("发表")
            ],
            1
          )
        ]
      ),
      _vm._v(" "),
      _c("Modal", { ref: "modal", attrs: { dialog: _vm.dialog } })
    ],
    1
  )
}
var staticRenderFns = []
render._withStripped = true

if (false) {
  module.hot.accept()
  if (module.hot.data) {
    require("vue-hot-reload-api")      .rerender("data-v-3b133c60", { render: render, staticRenderFns: staticRenderFns })
  }
}

/***/ })
],[298]);