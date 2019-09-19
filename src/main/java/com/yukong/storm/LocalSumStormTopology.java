package com.yukong.storm;

import lombok.extern.slf4j.Slf4j;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

import java.util.Map;

/**
 * storm实现累计求和
 * @author yukong
 * @date 2019-09-09 15:30
 */
public class LocalSumStormTopology {

	/**
	 * 定义数据源, Spout需要继承BaseRichSpout
	 * 数据源需要产生数据，并发射出去
	 */
	public static class DataSourceSpout extends BaseRichSpout{

		private SpoutOutputCollector spoutOutputCollector;

		private int num = 0;


		/**
		 * 初始化方法，只会被调用一次
		 * @param map 配置参数map
		 * @param topologyContext 上下文
		 * @param spoutOutputCollector 数据发射器
		 */
		@Override
		public void open(Map map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
			this.spoutOutputCollector = spoutOutputCollector;

		}

		/**
		 * 核心方法 用于产生数据
		 * 实际可以从 消息队列 kafka canal 获取
		 *
		 * 这个方法是死循环 一直运行
		 */
		@Override
		public void nextTuple() {
			this.spoutOutputCollector.emit(new Values(num++));
			// log.info("spout: {}", num);
			System.out.println("spout: " + num);

			// 防止数据发送硅块
			Utils.sleep(1000);

		}

		/**
		 * 申明输出的字段
		 * @param outputFieldsDeclarer
		 */
		@Override
		public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
			outputFieldsDeclarer.declare(new Fields("number"));
		}
	}

	public static class SumBolt extends BaseRichBolt {

		private int sum = 0;

		/**
		 * 初始化方法 只会被执行一次
		 * @param map
		 * @param topologyContext
		 * @param outputCollector
		 */
		@Override
		public void prepare(Map map, TopologyContext topologyContext, OutputCollector outputCollector) {

		}

		/**
		 * 死循环方法， 职责是获取spout发送过来的数据，处理数据
		 * @param tuple
		 */
		@Override
		public void execute(Tuple tuple) {
			Integer value = tuple.getIntegerByField("number");

			sum += value;

			// log.info("sum = " + sum);
			System.out.println("sum = " + sum);;
		}

		@Override
		public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {

		}
	}


	public static void main(String[] args) throws Exception {
		TopologyBuilder builder = new TopologyBuilder();

		builder.setSpout("DataSourceSpout", new DataSourceSpout());
		builder.setBolt("SumBolt", new SumBolt()).shuffleGrouping("DataSourceSpout");

		LocalCluster cluster = new LocalCluster();

		// 模拟创建一个本机的storm集群
		cluster.submitTopology("LocalSumStormTopology", new Config(), builder.createTopology());
	}
}
