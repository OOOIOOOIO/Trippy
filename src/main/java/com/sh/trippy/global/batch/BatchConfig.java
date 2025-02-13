//package com.sh.trippy.global.batch;
//
//import com.sh.trippy.domain.rule.delayrule.domain.DelayRule;
//import com.sh.trippy.domain.rule.delayrule.domain.repository.DelayRuleRepository;
//import com.sh.trippy.domain.rule.rule.domain.Rule;
//import com.sh.trippy.domain.rule.rule.domain.repository.RuleQueryRepositoryImpl;
//import com.sh.trippy.domain.rule.rule.domain.repository.RuleRepository;
//import com.sh.trippy.domain.user.domain.Users;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.configuration.annotation.JobScope;
//import org.springframework.batch.core.configuration.annotation.StepScope;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.item.ItemProcessor;
//import org.springframework.batch.item.data.RepositoryItemReader;
//import org.springframework.batch.item.data.RepositoryItemWriter;
//import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
//import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.domain.Sort;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import java.time.LocalDate;
//import java.util.Collections;
//import java.util.List;
//
//@Slf4j
//@Configuration
////@EnableBatchProcessing
//@RequiredArgsConstructor
//public class BatchConfig {
//
//    private final DelayToFailRuleTasklet delayToFailRuleTasklet;
//    private final RuleRepository ruleRepository;
//    private final RuleQueryRepositoryImpl ruleQueryRepository;
//    private final DelayRuleRepository delayRuleRepository;
//
//    /**
//     * 일정을 반복 타입에 맞게 만드는 Job1
//     */
//    @Bean
//    public Job createCheckCompleteRuleInfoJob(JobRepository jobRepository,
//                                              Step createCheckCompleteRuleInfoStep,
//                                              Step createDelayToFailRuleStep) {
//        return new JobBuilder("checkCompleteRuleInfo", jobRepository)
//                .start(createCheckCompleteRuleInfoStep)
//                .next(createDelayToFailRuleStep)
//                .build();
//    }
//
//
//
//    /**
//     * Step1 : delayGoal 생성
//     */
//    @Bean
//    @JobScope
//    public Step createCheckCompleteRuleInfoStep(JobRepository jobRepository,
//                                                PlatformTransactionManager transactionManager
//    ) {
//        return new StepBuilder("createCheckCompleteRuleInfoStep", jobRepository)
//                .<Rule, DelayRule>chunk(500, transactionManager)
//                .reader(trRuleReader())
//                .faultTolerant()
//                .skip(Exception.class)
//                .skipLimit(10)
//                .processor(trRuleProcessor())
//                .writer(trRuleWriter())
//                .faultTolerant()
//                .skip(Exception.class)
//                .skipLimit(5)
//                .retry(Exception.class) // exception 정해주기
//                .retryLimit(2)
//                .build();
//    }
//
//    @Bean
//    @StepScope
//    public RepositoryItemReader<Rule> trRuleReader(){
//        log.info("====== DELAY Goal Batch Reader =======");
//
//        return new RepositoryItemReaderBuilder<Rule>()
//                .name("trRuleReader")
//                .repository(ruleRepository)
//                .methodName("findAll")
//                .pageSize(500)
//                .arguments(List.of())
//                .sorts(Collections.singletonMap("ruleId", Sort.Direction.ASC))
//                .build();
//    }
//
//    /**
//     * 여기서 rci, rai 비교 후 delayGoal 생성
//     * @Transactional(propagation = Propagation.REQUIRES_NEW)
//     */
//    @Bean
//    @StepScope
//    public ItemProcessor<Rule, DelayRule> trRuleProcessor() {
//        return new ItemProcessor<Rule, DelayRule>() {
//            @Override
//            public DelayRule process(Rule rule) throws Exception {
//                log.info("====== DELAY Goal Batch Processor ======= ruleId : " + rule.getRuleId());
//
//
//                LocalDate yesterday = LocalDate.now().minusDays(1);
//                int year = yesterday.getYear();
//                int month = yesterday.getMonthValue();
//                int day = yesterday.getDayOfMonth();
//
//                Rule rci = ruleQueryRepository.findRuleCompleteInfoUsingYearAndMonth(year, month, rule.getRuleId()).orElse(null);
//                Rule rai = ruleQueryRepository.findRuleAlertInfoUsingYearAndMonth(year, month, rule.getRuleId()).orElse(null);
//
//                if(rci != null && rai != null){
//                    byte[] compDays = rci.getRuleCompleteInfoList().get(0).getCompleteDay();
//                    byte[] alertDay = rai.getRuleAlertInfoList().get(0).getAlertDay();
//                    // 알림일일 때
//                    if(alertDay[day] == 1){
//                        // 달성하지 못했다면
//                        if(compDays[day] == 0){
//                            // delayGoal 생성
//                            Users users = rule.getSmallGoal().getBigGoal().getUsers();
//                            return DelayRule.createDelayGoal(rule, users);
//                        }
//                    }
//                }
//
//                return null; // null일 경우 writer에 넘기지 낳음
//            }
//        };
//    }
//
//    @Bean
//    @StepScope
//    public RepositoryItemWriter<DelayRule> trRuleWriter() {
//        log.info("====== DELAY Goal Batch writer =======");
//        return new RepositoryItemWriterBuilder<DelayRule>()
//                .repository(delayRuleRepository)
//                .methodName("save")
//                .build();
//
////        return null;
//
//    }
//
//    /**
//     * ==========================================================================================
//     * ==========================================================================================
//     * ==========================================================================================
//     */
//
//    /**
//     * Step2 : failGoal 생성
//     */
//    @Bean
//    @JobScope
//    public Step createDelayToFailRuleStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
//        return new StepBuilder("createDelayToFailRuleStep", jobRepository)
//                .tasklet(delayToFailRuleTasklet, transactionManager)
//                .build();
//    }
//
//
//
//
//
//
//
//
//
//
//}
